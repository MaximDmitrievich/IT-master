from argparse import ArgumentParser
from simulator import Simulator

def main(items, order, cooking, end):
    sim = Simulator(items, order, cooking, end)
    sim.run()
    return 0

if __name__ == "__main__":
    parser = ArgumentParser(description='process path to file')
    parser.add_argument('items', metavar='ITEMS', help='count of items', type=int)
    parser.add_argument('order', metavar='ORDER', help='time of preparing order', type=int)
    parser.add_argument('cooking', metavar='COOKING', help='time of cooking order', type=int)
    parser.add_argument('end', metavar='END', help='end time', type=int)
    args = parser.parse_args()
    print('\n')
    main(args.items, args.order, args.cooking, args.end)
    print('\n')