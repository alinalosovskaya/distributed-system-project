# Distributed Messaging Framework
Celem projektu jest stworzenie prototypu rozproszonego systemu przesyłania wiadomości między aplikacjami. 

## Planowane funkcje
1. Adresowanie 1–n – nadawca będzie mógł wysłać jedną wiadomość do wielu odbiorców.
2. Architektura peer-to-peer – węzły będą równorzędne i będą samodzielnie obsługiwać komunikację.
3. Transport oparty na IP i transport bez IP – wspólny interfejs pozwoli korzystać z obu rodzajów transportu.
4. Priorytety wiadomości – priorytet będzie wpływał na kolejność wysyłania wiadomości oczekujących w kolejce.
5. Wiadomości o potencjalnie nieograniczonym rozmiarze – dane będą przesyłane w częściach. 

## Planowane technologie
1. Java – implementacja głównej części systemu.
2. TCP – pierwszy transport oparty na IP, z wykorzystaniem klas `ServerSocket` i `Socket`.
3. JSON – format komunikatów wymienianych między klientem a systemem.

## Etapy realizacji
1. Jeden serwer i jeden klient – obie aplikacje działają lokalnie. Klient wysyła przez TCP komunikat JSON. Serwer odczytuje cały komunikat i odsyła go w niezmienionej postaci. Klient sprawdza odpowiedź i kończy działanie.
2. Serwer współbieżny – serwer obsługuje jednocześnie połączenia od wielu klientów.
3. Komunikacja między węzłami – uruchamiamy kilka równorzędnych węzłów. Każdy z nich może przyjmować połączenia i nawiązywać je z innymi węzłami.

## Skład zespołu
1. Alina Lasouskaya
2. Tatiana Mikshta
