# Distributed Messaging Framework
Celem projektu jest stworzenie prototypu rozproszonego systemu przesyłania wiadomości między aplikacjami. 

## Planowane funkcje
1. Adresowanie 1–n – nadawca będzie mógł wysłać jedną wiadomość do wielu odbiorców.
2. Architektura peer-to-peer – węzły będą równorzędne i będą samodzielnie obsługiwać komunikację.
3. Transport oparty na IP i transport bez IP – wspólny interfejs pozwoli korzystać z obu rodzajów transportu.
4. Priorytety wiadomości – priorytet będzie wpływał na kolejność wysyłania wiadomości oczekujących w kolejce.
5. Wiadomości o potencjalnie nieograniczonym rozmiarze – dane będą przesyłane w częściach.
6. Niezależność od języka programowania – klienta będzie można napisać w dowolnym języku obsługującym TCP i JSON.

## Planowane technologie
1. Java – implementacja głównej części systemu.
2. TCP – pierwszy transport oparty na IP, z wykorzystaniem klas `ServerSocket` i `Socket`.
3. JSON – format komunikatów wymienianych między klientem a systemem.

## Etapy realizacji
1. Zrealizowano podstawową komunikację między jednym serwerem a jednym klientem. Serwer obsługiwał klientów sekwencyjnie, odczytywał JSON zapisany w jednej linii i odsyłał go do klienta.
2. Dodano serwer współbieżny – każde połączenie klienta jest obsługiwane w osobnym wątku.
3. Zaplanowano komunikację między równorzędnymi węzłami systemu.
4. Dodano parsowanie JSON z użyciem biblioteki Jackson oraz obsługę wieloliniowych komunikatów.
5. Dodano przesyłanie wielu wiadomości przez jedno połączenie TCP. Liczbę wiadomości określa parametr uruchomienia klienta.
6. Dodano sprawdzanie zgodności danych otrzymanej odpowiedzi z wysłanym komunikatem.

## Skład zespołu
1. Alina Lasouskaya
2. Tatiana Mikshta


## Wymagania

- JDK 17 lub nowszy.
- Apache Maven.
- Dostęp do internetu przy pierwszym pobieraniu zależności.

## Kompilacja

Otwórz terminal w katalogu `tcp-echo-json`, w którym znajduje się plik `pom.xml`, i wykonaj:

```bash
mvn compile dependency:copy-dependencies
```

Poprawne zakończenie kompilacji jest sygnalizowane komunikatem `BUILD SUCCESS`.

## Uruchamianie

Poniższe polecenia są przeznaczone dla macOS i Linux.
Należy je wykonywać w katalogu `tcp-echo-json`.

### Serwer sekwencyjny

```bash
java -cp "target/classes:target/dependency/*" tcpecho.SequentialServer
```

### Serwer współbieżny

```bash
java -cp "target/classes:target/dependency/*" tcpecho.ConcurrentServer
```

Uruchom jeden z serwerów. Oba korzystają z adresu `127.0.0.1` i portu `5000`.
Aby zakończyć działanie serwera, naciśnij `Ctrl+C`.

### Klient

W osobnym terminalu, w tym samym katalogu, wykonaj:

```bash
java -cp "target/classes:target/dependency/*" tcpecho.Client n
```
Zamiast `n` należy podać dodatnią liczbę całkowitą określającą liczbę wiadomości wysyłanych przez jedno połączenie TCP.
Bez podania parametru klient wysyła jedną wiadomość.

Klient wyświetla odpowiedzi serwera i sprawdza zgodność ich danych z wysłanymi wiadomościami.
