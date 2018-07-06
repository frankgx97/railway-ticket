package cn.guoduhao.TicketSystem.Models;

import javax.persistence.*;

@Entity
@Table
public class Ticket {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer id;
    public String name;
    public Ticket(String name){
        this.name = name;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
