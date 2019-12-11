class Customer:
    def __init__(self, number):
        self.number = number
    
    def come(self):
        return "Покупатель %s пришел" % self.number 
    
    def leave(self):
        return "Покупатель ушел" 

    def order(self, queue):
        if(not(q.empty())): 
            print("Покупатель",self.number,"встает в очередь"); 
        q.put(self.number)
        return self.number
    
    def wait(self,q):
        q.put(self.number)
        return "Покупатель %s ожидает выдачи заказа" % self.number