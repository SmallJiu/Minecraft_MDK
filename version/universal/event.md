# 什么是事件
* 套用[Harbinger](https://harbinger.covertdragon.team/chapter-03/)的解释 
  * 事件是某个时间点发生的有一定关注度的“事情”。

## 事件订阅
```java
// 此方法等同于 @EventBusSubscriber() 注解
MinecraftForge.EVENT_BUS.register(SimpleListener.class);
@EventBusSubscriber(// 此注解可自动订阅
        // 订阅端
        value = Dist.CLIENT,
        // 订阅的总线
        bus = Bus.FORGE
)
public class SimpleListener {
    // 订阅事件的方法返回值必须为void
    // 如果使用的是 @EventBusSubscriber 或者 .class 注册，则方法必须为 static
    @SubscribeEvent
    public static void onEvent(SimpleEvent event){
        // 处理过程
        if (
                event.isCancelable() // 是否是可取消的事件
             && !event.isCanceled() // 事件是否已被取消
        ) {
            // 如果取消不可取消的事件则会报错
            event.setCanceled(true);
        }
    }
}
// 此方法等同于上述的 @EventBusSubscriber 注解
MinecraftForge.EVENT_BUS.register(SimpleListener.class);
```

## 事件发布

### 普通事件
```java
@Cancelable // 此注解可让事件可取消
public class SimpleEvent extends Event {
    private int value;
    public SimpleEvent(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
}

int value = 998;
SimpleEvent event = new SimpleEvent(value);
// 若事件被取消则为true，不可取消则始终为false
if(MinecraftForge.EVENT_BUS.post(event)) {
    value = event.getValue();
}
System.out.println(String.format("Value: %s", value));
```

### 泛型事件
```java
// 继承 GenericEvent 可以添加一个泛型属性
public class SimpleGenericEvent<T extends Number> extends GenericEvent<T> {
    private final T value;
    public SimpleGenericEvent(T value) {
        super(value.getClass());
        this.value = value;
    }
    public T getValue() {
        return value;
    }
}
@EventBusSubscriber
public class SimpleListener {
    @SubscribeEvent
    public static void onEvent(SimpleGenericEvent<Integer> event) {
        // 此事件只会支持<Integer>的事件
        // 其他类型的不会接收
        // 如果是泛型的父类，则会接收所有关于此泛型的事件
    }
}
```
