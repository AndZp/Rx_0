package com.example.rx_0

interface Subject {

    //methods to register and unregister observers
    fun register(obj: Observer)

    fun unregister(obj: Observer)

    //method to notify observers of change
    fun notifyObservers(msg: String)
}

interface Observer {

    //method to update the observer, used by subject
    fun update(msg: String)

    //attach with subject to observe
    fun setSubject(sub: Subject)
}


class MyTopic : Subject {

    private val observers: MutableList<Observer>

    init {
        this.observers = ArrayList()
    }

    override fun register(obj: Observer) {

        if (!observers.contains(obj)) observers.add(obj)

    }

    override fun unregister(obj: Observer) {
        observers.remove(obj)

    }

    override fun notifyObservers(msg: String) {
        val observersLocal: List<Observer>?
        observersLocal = ArrayList(this.observers)
        for (obj in observersLocal) {
            obj.update(msg)
        }

    }

    //method to post message to the topic
    fun postMessage(msg: String) {
        println("Message Posted to Topic:$msg")
        notifyObservers(msg)
    }
}

class MyTopicSubscriber(private val name: String) : Observer {
    private var topic: Subject? = null

    override fun update(msg: String) {
        println("$name:: Consuming message::$msg")
    }

    override fun setSubject(sub: Subject) {
        this.topic = sub
    }
}


object ObserverPatternTest {

    @JvmStatic
    fun main(args: Array<String>) {
        //create subject
        val topic = MyTopic()

        //create observers
        val obj1 = MyTopicSubscriber("Obj1")
        val obj2 = MyTopicSubscriber("Obj2")
        val obj3 = MyTopicSubscriber("Obj3")

        //register observers to the subject
        topic.register(obj1)
        topic.register(obj2)
        topic.register(obj3)

        //attach observer to subject
        obj1.setSubject(topic)
        obj2.setSubject(topic)
        obj3.setSubject(topic)

        //now send message to subject
        topic.postMessage("New Message")
    }

}