from knn import KNeighborsClassifier
from argparse import ArgumentParser


# ./stations_update.csv ./transfers.csv ./data.csv

def main(station, k_neighbors, stations, transfers, beverage_data):
    knc = KNeighborsClassifier(stations, transfers, beverage_data)

    knc.fit(station, k_neighbors)
    pred = knc.predict()

    if pred['tea'] > pred['coffee']:
        print(f'На станции {station} пьют чай')
    elif pred['coffee'] > pred['tea']:
        print(f'На станции {station} пьют кофе')
    else:
        print(f'На станции {station} пьют и чай и кофе')

    return 0
    
if __name__ == "__main__":
    parser = ArgumentParser(description='process path to file')
    parser.add_argument('station', metavar='STATION', help='station for prediction', type=str)
    parser.add_argument('k', metavar='K', help='k nearest neighbors', type=int)
    parser.add_argument('stations', metavar='STATIONS', help='list of stations', type=str)
    parser.add_argument('transfers', metavar='TRANSFERS', help='list of transfers', type=str)
    parser.add_argument('beverage', metavar='BEVERAGE', help='list of beverages for indexing', type=str)
    args = parser.parse_args()
    print('\n')
    main(args.station, args.k, args.stations, args.transfers, args.beverage)
    print('\n')