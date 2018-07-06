package cn.guoduhao.TicketSystem.Models;

import javax.persistence.*;

@Entity
@Table
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    public String trainId;//车次
    public String departStation;//始发站
    public String destinationStaion;//到达站
    public String departTime;//出发时间
}