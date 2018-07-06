package cn.guoduhao.TicketSystem.Models;

import javax.persistence.*;

@Entity
@Table
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    public String trainId;//车次
}