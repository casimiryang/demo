package com.generic;

/**
 * 这里 type erasure后MyNode 其实没有覆写到Node的setData 方法，一个参数是Integer,一个是String, 是重载，
 * 所以当编译一个继承参数化类型的类的类实现参数化类型的接口的接口时为了保证Override, 编译器会给MyNode 生成一个 bridge  method
 *
 *
 * Bridge method generated by the compiler
     public void setData(Object data) {
        setData((Integer) data);
     }
     public void setData(Integer data) {
        super.setData(data);
     }
 */
public class Node<T>  {

    public T data;

    public Node(T data) { this.data = data; }

    public void setData(T data) {
        System.out.println("Node.setData");

    }

}


 class MyNode extends Node<Integer> {
    public MyNode(Integer data) { super(data); }


    public void setData(Integer data) {
        System.out.println("MyNode.setData");

    }

     public static void main(String args[]){
         MyNode mn = new MyNode(5);
         Node n = mn;            // A raw type - compiler throws an unchecked warning
         n.setData("Hello");    // Causes a ClassCastException to be thrown.
         Integer x = mn.data;
         System.out.println(n);
     }
}
