# Przetwarzanie Obrazów – JavaFX

## Autor

**Kacper Karkosz**

## Opis projektu

Aplikacja została wykonana w ramach **Laboratorium 6 – Wielowątkowość i GUI w Java** na przedmiot *Platformy Programistyczne .NET i Java*. Celem zadania było stworzenie aplikacji graficznej w JavaFX, umożliwiającej:

- wczytanie obrazu (.jpg),
- wykonanie jednej z wybranych operacji (lista rozwijana),
- zapisanie przetworzonego obrazu pod wybraną nazwą,
- zgodność z wymaganiami UX i określonymi historyjkami użytkownika.

## Zakres realizacji – Zadanie 1 (ocena 3.0)

### ✅ Zrealizowane funkcje:

- [x] **Aplikacja okienkowa** oparta o JavaFX.
- [x] **Wyświetlanie tekstu powitalnego** na ekranie głównym.
- [x] **Logo Politechniki Wrocławskiej** na górze aplikacji (`logo_pwr.png`) z obsługą przypadku braku pliku.
- [x] **Lista rozwijana** z wyborem operacji: `"Negatyw"`, `"Progowanie"`, `"Konturowanie"`, `"Obrót"`.
- [x] **Przycisk "Wykonaj"** z walidacją wyboru operacji (toast: "Nie wybrano operacji do wykonania").
- [x] **Wczytywanie obrazów JPG** z walidacją typu pliku.
- [x] **Komunikaty typu toast** przy różnych zdarzeniach (wczytanie pliku, błąd, itp.).
- [x] **Podgląd obrazu oryginalnego i przetworzonego** (na początku oba są identyczne).
- [x] **Przycisk "Zapisz"** aktywowany po wczytaniu obrazu.
- [x] **Okno modalne do zapisu obrazu** z walidacją długości nazwy i obsługą przypadków błędnych:
  - za krótka nazwa
  - plik już istnieje
  - zapis nie powiódł się
- [x] Zapis do lokalizacji: `~/Desktop`.

Aplikacja spełnia **wymagania Zadania 1**. Umożliwia podstawową obsługę graficznego interfejsu użytkownika, zgodnie z podejściem opartym na historyjkach użytkownika (ang. *user stories*). Przygotowuje grunt pod rozbudowę o dalsze funkcje, takie jak rzeczywiste przetwarzanie obrazu, skalowanie i wielowątkowość.
