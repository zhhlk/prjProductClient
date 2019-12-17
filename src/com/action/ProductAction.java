package com.action;
import java.util.*;
import java.io.*;
import java.net.*;
import com.bean.*;
public class ProductAction {
    private Socket socket;
    private Product pd;
    private Scanner input=new Scanner(System.in);
    private InputStream in;
    private OutputStream out;

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    /**
     * 输入商品信息的方法
     * */
    public  Product inputProductInfo(){
        Product pd=new Product();

        System.out.println("输入商品名称:");
        pd.setPname(input.next());

        System.out.println("输入商品的价格:");
        pd.setPrice(input.nextFloat());

        System.out.println("输入商品的数量:");
        pd.setNum(input.nextInt());

        return pd;
    }

    /***
     * 菜单
     * */
    public void showMenu(){
        try {
            while(true){
                socket=new Socket("172.16.0.172",8899);
                in=socket.getInputStream();
                out=socket.getOutputStream();

                System.out.println("=====商品信息管理===");
                System.out.println("1--增加商品");
                System.out.println("2--修改商品");
                System.out.println("3--删除商品");
                System.out.println("4--查找商品");
                System.out.println("5--显示商品");
                System.out.println("0--退出系统");
                System.out.println("===================");
                System.out.println("请输入您的选择0--5：");
                int op=input.nextInt();

                out.write(op);//向服务器发送操作码
                out.flush();

                switch (op){
                    case 1:
                        System.out.println("商品信息管理>>增加商品");

                        pd=inputProductInfo();//输入商品信息
                        //发送要保存的商品对象到服务器
                        objectOutputStream=new ObjectOutputStream(out);
                        objectOutputStream.writeObject(pd);
                        objectOutputStream.flush();

                        //获取服务器返回的结果码
                        int code=in.read();
                        if(code==1){
                            System.out.println("服务器保存成功！");
                        }else{
                            System.out.println("服务器保存失败！");
                        }


                        break;
                    case 2:
                        System.out.println("商品信息管理>>修改商品");
                        pd=inputProductInfo();//输入商品信息

                        //输入商品编号
                        System.out.println("请输入要修改的商品编号：");
                        int pid=input.nextInt();
                        pd.setPid(pid);

                        //发送要保存的商品对象到服务器
                        objectOutputStream=new ObjectOutputStream(out);
                        objectOutputStream.writeObject(pd);
                        objectOutputStream.flush();

                        //获取服务器返回的结果码
                        code=in.read();
                        if(code==1){
                            System.out.println("服务器更新成功！");
                        }else{
                            System.out.println("服务器更新失败！");
                        }

                        break;
                    case 3:
                        System.out.println("商品信息管理>>删除商品");
                        //输入商品编号
                        System.out.println("请输入要删除的商品编号：");
                        pid=input.nextInt();

                        //向服务器发送要删除的商品编号
                        out.write(pid);
                        out.flush();
                        //获取服务器返回的结果码
                        code=in.read();
                        if(code==1){
                            System.out.println("服务器删除成功！");
                        }else{
                            System.out.println("服务器删除失败！");
                        }



                        break;
                    case 4:
                        System.out.println("商品信息管理>>查找商品");
                        //输入商品编号
                        System.out.println("请输入要查询的商品编号：");
                        pid=input.nextInt();

                        //向服务器发送商品编号
                        out.write(pid);
                        out.flush();

                        //从服务器获取返回的商品对象
                        objectInputStream =new ObjectInputStream(in);
                        pd= (Product) objectInputStream.readObject();

                        if(pd!=null){
                            System.out.println("编号\t品名\t价格\t数量\t小计");
                            System.out.println("=======================================");
                            System.out.println(pd.getPid()+"\t"
                                    +pd.getPname()+"\t"
                                    +pd.getPrice()+"\t"
                                    +pd.getNum()+"\t"
                                    +pd.getAcount()
                            );
                            System.out.println("=======================================");
                        }else{
                            System.out.println("没有找到该编号的商品信息！");
                        }
                        break;
                    case 5:
                        System.out.println("商品信息管理>>显示商品");
                        //从服务器返回所有的商品集合对象
                        objectInputStream =new ObjectInputStream(in);
                        Map<Integer,Product> map= (Map<Integer, Product>) objectInputStream.readObject();
                        if(map==null){
                            System.out.println("无任何商品信息存在，请先添加！");
                            continue;
                        }
                        Collection<Product> coll=map.values();
                        System.out.println("编号\t品名\t价格\t数量\t小计");
                        System.out.println("=======================================");
                         coll.stream().forEach(pd-> System.out.println(pd.getPid()+"\t"
                                 +pd.getPname()+"\t"
                                 +pd.getPrice()+"\t"
                                 +pd.getNum()+"\t"
                                 +pd.getAcount()));
                        System.out.println("=======================================");
                        break;
                    case 0:
                        System.out.println("谢谢使用，系统退出！");
                        System.exit(0);//退出应用程序
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
