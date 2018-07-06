package cn.guoduhao.TicketSystem.Models;

import javax.persistence.*;

@Entity
@Table
public class Ticket {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer id;
    public String name;//姓名
    public String order_id;//订单编号
    public String time;//时间
    public String start;//起点
    public String end;//终点
    public Integer expense;//车费
    public Integer status;//车票状态
    public String train_id;//车次
    public String seat;//座位

    public Ticket(String name){
        this.name = name;
    }
}
