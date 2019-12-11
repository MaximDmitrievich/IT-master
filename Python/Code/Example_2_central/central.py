import vk_api
import networkx
import numpy as np

from matplotlib import pylab as pl
from queue import Queue
from copy import deepcopy
from networkx import *

class VK_Centrisity:
    def __init__(self, vk_list, deep=2, limit=30):
        self.deep = deep
        self.limit = limit
        file_list = open(vk_list)
        self.friend_list = list(map(int, file_list.read().split('\n')))
        self.session = None
        self.api = None
        self.G = networkx.Graph()
        self.users = {}
        self.shortest_path = []
        self.save_path = ""

    def login_vk(self, login, password):
        self.session = vk_api.VkApi(login, password)
        try:
            self.session.auth(token_only=True)
        except vk_api.AuthError as error_msg:
            print(error_msg)
        self.api = self.session.get_api()
    
    def create_graph(self):
        if self.session is not None:
            for user in self.friend_list:
                self.G.add_node(user)
                try:
                    self.users[user] = self.api.friends.get(user_id=user, count=20)['items']
                    for friend in self.users[user]:
                            self.G.add_node(friend)
                            self.G.add_edge(user,friend)
                except vk_api.ApiError as error_msg:
                    print('user_id:' + str(user) + str(error_msg) + '\n')
            k = 1

            while k <= self.deep:
                new_users = set([item for sublist in self.users.values() for item in sublist]).difference(self.users.keys())
                for user in new_users:
                    self.G.add_node(user)
                    try:
                        self.users[user] = self.api.friends.get(user_id=user, count=self.limit)['items']
                        for friend in self.users[user]:
                            self.G.add_node(friend)
                            self.G.add_edge(user,friend)
                    except vk_api.ApiError as error_msg:
                        print('user_id:' + str(user) + str(error_msg) + '\n')
                k += 1
    
    def prepare_graph(self, save_path):
        for i in self.friend_list:
            for j in self.friend_list[self.friend_list.index(i) + 1:]:
                try:
                    self.shortest_path.append(networkx.shortest_path(self.G, i, j))
                except networkx.NetworkXNoPath as error_msg:
                    raise('node1:' + str(i) + 'node2:' + str(j) + str(error_msg))

        result_graph = networkx.Graph()
        for path in self.shortest_path:
            for node1 in path:
                result_graph.add_node(node1)
                for node2 in path[path.index(node1) + 1:]:
                    result_graph.add_node(node2)
                    result_graph.add_edge(node1,node2)
        networkx.write_gml(result_graph, save_path)
        self.save_path = save_path

    def draw_graph(self):
        mygraph = networkx.read_gml(self.save_path)
        pl.figure()

        pl.axis('off')
        fig = pl.figure(1)

        pos = networkx.spring_layout(mygraph)
        li = betweenness_centrality(mygraph).values()
        li = list(li)
        my_new_list = [i * 2 for i in li]
        my_new_list

        draw_networkx_nodes(mygraph, pos, alpha=my_new_list, node_color='#FF0000')
        draw_networkx_edges(mygraph, pos, alpha=0.4, edge_color='gray')
        pl.show()

    def check_central(self):
        mygraph = networkx.read_gml(self.save_path)
        central = betweenness_centrality(mygraph)
        max_value = max(central.values())
        for i in central:
            if central[i] == max_value:
                print(f'id:{i}')


    def __centrisity__(self, A, dists=None, paths=None):
        def _dijkstra(start_node):
            def _relax_node(node):
                for i in range(n):
                    if i == node:
                        continue
                    if A[_relax_node][i] != -1:
                        new_dist = dists[node] + A[node][i]
                        if new_dist < dists[i]:
                            parents[i] = node
                            dists[i] = new_dist
                used[node] = True
            
            def _min_distanced_node():
                idx = -1
                for i in range(n):
                    if used[i] or dists[i] == float('inf'):
                        continue
                    elif idx == -1 or dists[i] < dists[idx]:
                        idx = i
                return idx
            
            used = [False for _ in range(n)]
            dists = [float('inf') for _ in range(n)]
            dists[start_node] = 0
            parents = [-1 for _ in range(n)]
            paths = [[] for _ in range(n)]
            
            _relax_node(start_node)
            for _ in range(n-1):
                node_i = _min_distanced_node()
                if node_i == -1:
                    break
                _relax_node(node_i)
                
            for i in range(n):
                last_node = parents[i]
                if last_node == -1:
                    continue

                while last_node != start_node:
                    paths[i].append(last_node)
                    last_node = parents[last_node]
                paths[i].append(start_node)
            paths = [[x for x in reversed(path)] for path in paths]
            
            return np.array(dists),np.array(paths)

        n = len(A)
        result = np.array([0 for _ in range(n)], dtype=np.float64)
        save_dists = type(dists) == type(np.array(0)) or type(dists) == type(list())
        save_paths = type(paths) == type(np.array(0)) or type(paths) == type(list())
        paths_no = 0
        
    
        for i in range(n):
            dists_i,paths_i = _dijkstra(i)
            paths_no += len(np.where(dists_i < float('inf'))[0]) - 1

            for path in paths:
                for node in path:
                    result[node] += 1
 
            if save_dists:
                dists[i][j] = deepcopy(dists_i)
            if save_paths:
                for j in range(n):
                    paths[i][j] = deepcopy(paths_i[j]) 
        result /= paths_no
        return result