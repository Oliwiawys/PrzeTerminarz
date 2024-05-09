1 PrzeTerminarz

Celem zadania jest stworzenie aplikacji mobilnej, która umożliwi użytkownikom zbieranie i zarządzanie informacjami o terminach ważności różnych produktów w kategoriach: Produkty spożywcze, Leki oraz Kosmetyki. Aplikacja ma pomóc w redukcji marnowania produktów poprzez śledzenie ich dat ważności i ułatwienie ich zużycia przed upływem terminu.

1.1 Ekran listy:

• aplikacja powinna wyświetlać listę wprowadzonych produktów z możliwością filtrowania według kategorii oraz stanu produktu (ważny/przeterminowany)

• element listy powinny zawierać: nazwę produktu, termin ważności, kategorię oraz ilość (jeśli została określona)

• lista powinna być automatycznie sortowana według daty ważności produktów, od najkrótszej do najdłuższej

• ekran powinien zawierać podsumowanie dla puli prezentowanych elementów (ich liczbę)

• wybór elementu listy umożliwi jego podgląd i edycję, tylko jeśli produkt jest nadal przydatny, w przeciwnym wypadku wyświetli stosowną informację o braku możliwości edycji takiego produktu

• dłuższe przytrzymanie spowoduje pokazanie alertu z zapytaniem o usunięcie elementu z listy (jeśli produkt jest dalej ważny). Jeśli użytkownik zatwierdzi usunięcie wpis powinien zniknąć z listy, a podsumowanie się aktualizować

• ekran powinien zawierać również przycisk umożliwiający dodanie nowego elementu

• lista powinna być zrealizowana implementacja komponentu graficznego RecyclerView


1.2 Ekran dodawania/edycji produktu:


• uruchamia się w następstwie kliknięcia przycisku dodającego lub w przypadku edycji istniejącego wpisu na liście

• ekran ten umożliwia nadanie/zmianę danych wpisu oraz zawiera przycisk zapisujący dokonane zmiany/wprowadzone dane

• kategoria wybierana przy pomocy rozwijanej listy wyboru lub radio buttonów

• data ważności wprowadzana przy pomocy kontrolki date picker

• ekran waliduje wprowadzane dane (data nie może być przeszła, nazwa pusta, jesli użytkownik zdecyduje się na wprowadzenie ilości to ilość musi być wartością liczbową, a jednostka nie może być pominięta)

• edycja produktu nie może odbywać poprzez usunięcie starego wpisu i dodanie go ponownie ze zmienioną zawartością

Zapisywanie danych w pamięci stałej nie należy do zakresu tego zadania. Wprowadzone zmiany mogą się restartować przy każdym zamknięciu aplikacji. 

Przed poddaniem projektu ocenie należy przygotować zestaw danych przykładowych wczytywany każdorazowo podczas uruchomienia aplikacji, w celu zaprezentowania wszystkich funkcjonalności. (najlepiej dane te umieścić bezpośrednio w kodzie aplikacji)
