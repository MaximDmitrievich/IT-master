from argparse import ArgumentParser

def main(list_path, login, password, save_path):
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