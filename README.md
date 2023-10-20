# Simple JDBC sample

---

```
docker run -d --name pg-jdbc -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=uporabniki -p 5432:5432 postgres:13
```

**Mikrostoritev Upravljanje uporabnikov preko API-ja**

Mikrostoritev omogoča naslednje funkcionalnosti preko vmesnika (API):

* Pridobivanje uporabnikov iz baze
* *Dodajanje uporabnikov*
* <u>Urejanje uporabnikov</u>
* ***Brisanje uporabnikov***
