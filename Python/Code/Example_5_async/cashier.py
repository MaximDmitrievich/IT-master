from time import sleep
from random import randint
from customer import Customer

class Cashier:
    def __init__(self, q, name):
        self.name = name
        self.q = q

    def service(self):
        print(f"Кассир {self.name} начинает обслуживать покупателя")

    def servcomplete(self,qd):
        amount = randint(4, 7)
        sleep(amount)
        self.cu = self.q.get()
        self.cust = Customer(self.cu)
        print("Кассир", self.name, "завершил обслуживание покупателя ", self.cu)
        print((self.cust).wait(qd))


    def shout(self):
        return '%s кричит: "Свободная касса!"' % self.name

    def turn(self, q):
        self.q = q
        return q