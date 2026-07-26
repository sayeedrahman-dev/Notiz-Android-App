# Home Screen: Notes List & Grid View Plan

Bismillah.

Alhamdulillah, apnar app-ta thikmoto run hoyeche ebong navigation kaj korche—eta ekta boro shofolota! Ekhon amra apnar app-er main feature-e hat dibo: **Notes dekhano**.

Figma design onujayi, jokhon user note likhbe, tokhon seta grid view-te sundorvabe dekhate hbe.

## User Review Required

> [!IMPORTANT]
> Amra ekhon `EmptyHomeScreen.kt`-ke refine kore ekta dynamic **`HomeScreen.kt`** banabo jeta automatic bujhte parbe note ache ki nei.

## Proposed Changes

### [Component Name] - Home Experience

#### [NEW] [NoteCard.kt](file:///D:/Notiz/app/src/main/java/com/sayeed_dev/notiz/ui/components/NoteCard.kt)
Figma-er moto ekta sundor card design kora jekhane Note-er Title, Content ebong Date thakbe.

#### [RENAME & MODIFY] [HomeScreen.kt](file:///D:/Notiz/app/src/main/java/com/sayeed_dev/notiz/ui/screen/HomeScreen.kt)
`EmptyHomeScreen.kt` file-tar nam change kore `HomeScreen.kt` kora hobe ebong ekhane logic add kora hbe:
- **Condition:** Jodi `notes.isEmpty()` hoy, tobe "Empty State" dekhabe.
- **Condition:** Jodi `notes.isNotEmpty()` hoy, tobe "Grid List" dekhabe.

#### [MODIFY] [NavGraph.kt](file:///D:/Notiz/app/src/main/java/com/sayeed_dev/notiz/ui/navigation/NavGraph.kt)
Renamed `HomeScreen`-er sathe connection update kora.

---

## Verification Plan

### Manual Verification
- App-e login korar por Home-e jawon.
- Dummy data add kore dekha je notes gulo grid-e thikmoto asche kina.
- Pinned notes-er header thikmoto dekhachhe kina check kora.

Alhamdulillah. Apni ki Notes List design shuru korar jonno ready?
