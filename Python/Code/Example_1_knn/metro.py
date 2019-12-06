import csv

from math import fabs

class Station:
    def __init__(self, names, neighbors, beverage, station_id, line_id):
        self.names = names
        self.neighbors = neighbors
        self.beverage = beverage
        self.coverage_marker = False
        self.station_id = int(station_id)
        self.line_id = int(line_id)

    def __eq__(self, other):
        for name in self.names:
            if name in other.names:
                return True
        return False

    def remove_repetition(self):
        i = 0
        while i < len(self.names):
            j = i + 1
            while j < len(self.names):
                if self.names[i] == self.names[j]:
                    del self.names[j]
                else:
                    j += 1
            j = 0
            while j < len(self.neighbors):
                if self.names[i] == self.neighbors[j]:
                    del self.neighbors[j]
                else:
                    j += 1
            i += 1
        i = 0
        while i < len(self.neighbors):
            j = i + 1
            while j < len(self.neighbors):
                if self.neighbors[i] == self.neighbors[j]:
                    del self.neighbors[j]
                else:
                    j += 1
            i += 1
    