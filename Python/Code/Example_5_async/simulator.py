from customer import Customer
from queue import Queue, PriorityQueue
from collections import namedtuple

class Simulator:
    def __init__(self, queue_items, time_of_order, time_of_cooking, end_time):
        self.queue_items = queue_items
        self.time_of_cooking = time_of_cooking
        self.time_of_order = time_of_order
        self.end_time = end_time
        self.cashier = Queue()
        self.delivery = Queue()
        self.events = PriorityQueue()
        self.procs = dict()
        self.Event = namedtuple('Event', 'time proc state prev_state')

    def __customer_proc__(self, cus_id, start_time=0):
        cust = Customer(cus_id, start_time, 'come to order queue', 'none')
        self.cashier.put(cust)
        t, st, pr = cust.get_state()
        time = yield self.Event(t, cust.get_id(), st, pr)
        while cust is not self.cashier.queue[0]:
            time = yield self.Event(time, cust.get_id(), 'waiting order', 'come to order queue')
            cust.set_state(time, 'waiting order', 'come to order queue')
        else:
            time = yield self.Event(time, cust.get_id(), 'making order', 'waiting order')
            cust.set_state(time, 'making order', 'waiting order')
            cust = self.cashier.get()
            time = yield self.Event(time, cust.get_id(), 'come to cooking queue', 'making order')
            cust.set_state(time, 'come to cooking queue', 'making order')
            self.delivery.put(cust)
        while cust is not self.delivery.queue[0]:
            time = yield self.Event(time, cust.get_id(), 'waiting cooking', 'come to cooking queue')
            cust.set_state(time, 'waiting cooking', 'come to cooking queue')
        else:
            time = yield self.Event(time, cust.get_id(), 'getting order', 'waiting cooking')
            cust.set_state(time, 'getting order', 'waiting cooking')
            time = yield self.Event(time, cust.get_id(), 'going to eat', 'getting order')
            cust.set_state(time, 'going to eat', 'getting order')
            time = yield self.Event(time, cust.get_id(), 'none', 'going to eat')
            cust.set_state(time, 'none', 'going to eat')
            cust = self.delivery.get()
    
    def __compute_duration__(self, prev_act):
        if prev_act == 'making order':
            interval = self.time_of_order
        elif prev_act == 'getting order':
            interval = self.time_of_cooking
        elif prev_act in ['going to eat', 'none', 'waiting order', 'waiting cooking']:
            interval = 1
        elif prev_act == 'come to order queue':
            interval = self.time_of_order * self.cashier.qsize()
        elif prev_act == 'come to cooking queue':
            interval = self.time_of_cooking * self.delivery.qsize()
        else:
            raise ValueError('Unknown prev_act: %s' % prev_act)
        return interval

    def run(self):
        self.procs = dict({i: self.__customer_proc__(i) for i in range(1, self.queue_items)})
        for _, proc in sorted(self.procs.items()):
            first_event = next(proc)
            self.events.put(first_event)
            

        print('*** Start simulation ***\n')
        print('time\tid\tstate')
        sim_time = 0
        while sim_time < self.end_time:
            if self.cashier.qsize() == 0 and self.delivery.qsize() == 0:
                print('*** end of customers ***')
                break
            current_event = self.events.get()
            sim_time, proc_id, state, prev_state = current_event
            print(f'{sim_time}\t{proc_id}\t{state}')
            active = self.procs[proc_id]
            next_time = sim_time + self.__compute_duration__(prev_state)
            try:
                next_event = active.send(next_time)
            except StopIteration:
                del self.procs[proc_id]
            else:
                self.events.put(next_event)
        else: 
            print('*** End simulation ***')

        