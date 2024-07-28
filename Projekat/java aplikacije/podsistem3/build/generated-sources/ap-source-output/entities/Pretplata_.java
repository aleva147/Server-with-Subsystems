package entities;

import entities.Korisnik;
import entities.Paket;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-06-19T21:32:25")
@StaticMetamodel(Pretplata.class)
public class Pretplata_ { 

    public static volatile SingularAttribute<Pretplata, Korisnik> idKor;
    public static volatile SingularAttribute<Pretplata, Date> datumvreme;
    public static volatile SingularAttribute<Pretplata, Paket> idPak;
    public static volatile SingularAttribute<Pretplata, Integer> id;
    public static volatile SingularAttribute<Pretplata, Integer> cena;

}