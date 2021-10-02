package com.example.lib_base.event


import org.greenrobot.eventbus.EventBus


//EventBus管理
object ManagerEvent {
    fun register(subscribe: Any){
        //注册
        EventBus.getDefault().register(subscribe);
    }
    //解绑
    fun unRegister(subscribe: Any){
        //注册
        EventBus.getDefault().unregister(subscribe);
    }
    //发送事件类
    private fun post(event: MassageEvent){
        EventBus.getDefault().post(event)
    }
    //发送类型
    fun post(type:Int){
        post(MassageEvent(type))
    }
    //发送类型 携带String
    fun post(type:Int,string: String){
        var event = MassageEvent(type)
        event.stringValue = string
        post(event)
    }
    //发送类型 携带Int
    fun post(type:Int,int: Int){
        var event = MassageEvent(type)
        event.intValue = int
        post(event)
    }
    //发送类型 携带Boolean
    fun post(type:Int,boolean: Boolean){
        var event = MassageEvent(type)
        event.booleanValue = boolean
        post(event)
    }
}