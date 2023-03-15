# System rejestracji wizyt w gabinecie stomatologicznym
Zamieszczona aplikacja została stworzona jako projekt końcowy w ramach 12 edycji studiów podyplomowych "Nowoczesne aplikacje biznesowe Java/Jakarta EE".
Umożliwia ona tworzenie i rezerwację wizyt w gabinecie stomatologicznym.
W projekcie użyto poniższych technologii:
  • Java 17 (OpenJDK)
  • Jakarta EE 9.1
  • Kontener EJB 3.2,
  • JPA 2.5,
  • PostgreSQL 14.2,
  • JavaServer Faces 2.3,
  • BootsFaces 1.5.0

W celu uruchomienia aplikacji należy pobrać plik "gabinet.war"

Instrukcja wdrożenia
Do uruchomienia aplikacji wymagane jest środowisko:
  • Komputer z zainstalowanym systemem operacyjnym Windows 10,
  • przeglądarka internetowa (preferowane Google Chrome),
  • serwer aplikacyjny Payara Glassfish w wersji min. 5.2022.2,
  • PostgreSQL w wersji min. 14 wraz z narzędzien pgAdmin 4

Utworzenie bazy danych:
1. Na uruchomionym systemie operacyjnym należy zacząć od pobrania i zainstalowania
PostgreSQL oraz pgAdmin 4.
2. Należy uruchomić aplikację pgAdmin.
3. W następnym kroku należy kliknąć na drzewo Servers → PostgreSQL 14 → PPM na
Databases → Create → Database…
4. W menu w polu „Database” należy wpisać „dentist” i wcisnąć przycisk „Save”
5. Baza danych powinna pojawić się w drzewie po lewej stronie.

Wdrożenie aplikacji na serwerze aplikacyjnym Payara:
1. Należy uruchomić serwer aplikacyjny, w tym celu należy wejść do folderu gdzie został
zainstalowany serwer aplikacyjny Payara i wejście do katalogu bin.
2. W katalogu bin należy odpalić plik „asadmin.bat”.
3. Po uruchomieniu pliku pojawi się konsola, w której wpisujemy „start-domain”.
4. Jeśli wszystko poszło dobrze powinien pojawić się komunikat „Command startdomain
executed successfully”. Oznacza to, iż serwer został uruchomiony.
5. W tym momencie powinno się uruchomić przeglądarkę i wpisać adres
„http://localhost:4848/”.
6. Z lewego menu wybierać opcję „Applications”.
7. W tabeli należy nacisnąć przycisk „Deploy”.
8. W sekcji „Location” należy zaznaczyć „Packaged File to Be Uploaded to the Server” i
kliknać przycisk „Wybierz plik”. W tym miejscu otworzy się okno, z którego należy
wybrać lokalizację archiwum .war w którym znajduje się aplikacja „gabinet” (plik
„gabinet.war”).
9. Należy kliknąć przycisk „OK” poniżej tabeli i zaczekać na załadowanie się pliku.
10. Jeśli wszystko zadziałało w tabeli powinien pojawić się wiersz z nazwą aplikacji.
11.W tym wierszu należy kliknąć w na „Launch” w kolumnie „Action”
12.Powinna pojawić się strona „Web Application Links” i klikamy na jeden z podanych
poniżej linków. Aplikacja jest w gotowa do użycia.
