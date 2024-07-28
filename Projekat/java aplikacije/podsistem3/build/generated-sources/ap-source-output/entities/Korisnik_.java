package entities;

import entities.Gledanje;
import entities.Ocena;
import entities.Pretplata;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-06-19T21:32:25")
@StaticMetamodel(Korisnik.class)
public class Korisnik_ { 

    public static volatile ListAttribute<Korisnik, Gledanje> gledanjeList;
    public static volatile ListAttribute<Korisnik, Pretplata> pretplataList;
    public static volatile ListAttribute<Korisnik, Ocena> ocenaList;
    public static volatile SingularAttribute<Korisnik, Integer> id;

}