import threading

from random import randint

class Customer(threading.Thread):
    def __init__(self, start_time, state, prev_state):
        threading.Thread.__init__(self)
        self.id = randint(1, 100)
        self.time = start_time
        self.state = state
        self.prev_state = prev_state

    def set_state(self, time, state, prev_state):
        self.time = time
        self.state = state
        self.prev_state = prev_state

    def get_state(self):
        return self.time, self.state, self.prev_state

    def get_id(self):
        return self.id
