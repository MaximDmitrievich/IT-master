from argparse import ArgumentParser
from central import VK_Centrisity

def main(list_path, login, password, save_path):
    vk_c = VK_Centrisity(list_path)
    vk_c.login_vk(login=login, password=password)
    vk_c.create_graph()
    vk_c.prepare_graph(save_path)
    vk_c.check_central()
    return 0

if __name__ == "__main__":
    parser = ArgumentParser(description='process path to file')
    parser.add_argument('list', metavar='LIST', help='list of friends', type=str)
    parser.add_argument('login', metavar='LOGIN', help='vk login', type=str)
    parser.add_argument('password', metavar='PASSWORD', help='vk password', type=str)
    parser.add_argument('save', metavar='SAVE', help='link to save list', type=str)
    args = parser.parse_args()
    print('\n')
    main(args.list, args.login, args.password, args.save)
    print('\n')