import csv
from math import fabs

from metro import Station

class KNeighborsClassifier:
    def __find_station__(self, station_id):
        for station in self.stations:
            if station.station_id == station_id:
                return self.stations.index(station)

        return -1

    def __find_station_name__(self, name):
        for station in self.stations:
            if name in station.names:
                return self.stations.index(station)

        return -1

    def __combine_stations__(self, transfers_inf):
        for tr_inf in transfers_inf[2:]:
            if tr_inf != '':
                station_index1 = self.__find_station__(int(transfers_inf[1]))
                station_index2 = self.__find_station__(int(tr_inf))
                if station_index1 + station_index2 > 0 and self.stations[station_index1].names[0] != self.stations[station_index2].names[0]:
                    self.stations[station_index1].names += self.stations[station_index2].names
                    self.stations[station_index1].neighbors += self.stations[station_index2].neighbors
                    del self.stations[station_index2]

    def __repetition_correction__(self):
        station_id_1 = 0
        while station_id_1 < len(self.stations):
            station_id_2 = station_id_1 +1
            while station_id_2 < len(self.stations):
                if self.stations[station_id_1] == self.stations[station_id_2]:
                    self.stations[station_id_1].names += self.stations[station_id_2].names
                    self.stations[station_id_1].neighbors += self.stations[station_id_2].neighbors
                    del self.stations[station_id_2]
                else:
                    station_id_2 += 1
            station_id_1 += 1

    def __add_beverage__(self, data):
        with open(data, "r", encoding="utf8") as f_obj:
            reader = csv.reader(f_obj)
            for bev_inf in reader:
                station_id = self.__find_station_name__(bev_inf[2])
                if station_id != -1:
                    self.stations[station_id].beverage[bev_inf[3].lower()] += 1

    def __init__(self, lines, transfers, data):
        self.covered = []
        self.stations = []
        with open(lines, "r", encoding="utf8") as f_obj:
            reader = csv.reader(f_obj)
            for station_inf in reader:
                if station_inf[0] != '' and station_inf[3] != '':
                    self.stations.append(Station([station_inf[1]], [], {'tea' :0, 'coffee' : 0}, station_inf[0], station_inf[3]))
        for index, st in enumerate(self.stations[:-1]):
            if (self.stations[index].line_id == self.stations[index + 1].line_id) and (fabs(int(st.station_id) - int(self.stations[index + 1].station_id)) == 1):
                self.stations[index].neighbors += self.stations[index + 1].names
                self.stations[index + 1].neighbors += st.names
        with open(transfers, "r", encoding="utf8") as f_obj:
            reader = csv.reader(f_obj)
            for transfers_inf in reader:
                if transfers_inf[1].isdigit():
                    self.__combine_stations__(transfers_inf)
        self.__repetition_correction__()
        for station in self.stations:
            station.remove_repetition()
        self.__add_beverage__(data)

    def fit(self, station, k_neighbors):
        for st in self.stations:
            if station in st.names:
                self.covered.append([st])
                st.coverage_marker = True

        for i in range(0, k_neighbors):
            neighbors = []

            for st in self.covered[i]:
                neighbors += st.neighbors

            nghbrs = []

            for st in self.stations:
                for name in st.names:
                    if (name in neighbors) and (st.coverage_marker == False):
                        nghbrs.append(st)
                        st.coverage_marker = True
                        break
                self.covered.append(nghbrs)
                
        for st in self.stations:
            st.coverage_marker = False

    def predict(self):

        result = {'tea' : 0, 'coffee' : 0}

        for level in self.covered:
            if self.covered.index(level) == 0:
                for station in level:
                    result['coffee'] += station.beverage['coffee']
                    result['tea'] += station.beverage['tea']

        if result['tea'] == result['coffee']:
            for level in self.covered:
                if self.covered.index(level) == 0:
                    for station in level:
                        result['coffee'] += station.beverage['coffee']
                        result['tea'] += station.beverage['tea']
                else:
                    for station in level:
                        result['coffee'] += station.beverage['coffee'] / self.covered.index(level) ** 2
                        result['tea'] += station.beverage['tea'] / self.covered.index(level) ** 2
        return result
