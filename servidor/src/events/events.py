from abc import ABC, abstractmethod

class Event:
    def __init__(self):
        self.observers = []

    def suscribe(self, observer):
        if isinstance(observer, EventListener) \
                and observer not in self.observers:
            self.observers.append(observer)
    
    def unsuscribe(self, observer):
        if observer in self.observers:
            self.observers.remove(observer)
    
    def clear_observers(self):
        self.observers.clear()

    def trigger(self, **kwargs):
        for observer in self.observers:
            observer.notify(**kwargs)

TOKEN_VERIFIED_EVENT = Event() 

class EventListener(ABC):
    def __init__(self, event: Event):
        event.suscribe(self)

    @abstractmethod
    def notify(self, **kwargs):
        pass

class TokenVerifiedEventListener(EventListener):
    _user_id = ""
    _token = ""
    def __init__(self, *args, **kwargs):
        super().__init__(TOKEN_VERIFIED_EVENT)

    def notify(self, user_id, token):
        TokenVerifiedEventListener._user_id = user_id
        TokenVerifiedEventListener._token = token

