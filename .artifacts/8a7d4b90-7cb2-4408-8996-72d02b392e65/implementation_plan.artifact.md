# Permanent Data Storage: Room Database Integration

Bismillah.

Alhamdulillah, apnar app-er UI ebong Navigation ekhon ekdom solid! Ekhon amader ashol target holo app-ke **"Permanent"** banano. Er mane holo, user jokhon note save korbe, seta jate phone-er memory-te thake ebong app bondho korle muche na jay.

Ekjon professional developer eta korar jonno Android-er official **Room Database** use kore.

## Proposed Changes

### [Component Name] - Data Layer (Room DB)

#### [MODIFY] [libs.versions.toml](file:///D:/Notiz/gradle/libs.versions.toml) & [build.gradle.kts](file:///D:/Notiz/app/build.gradle.kts)
Database library (**Room**) ebong tar annotations processor add kora hbe.

#### [MODIFY] [Note.kt](file:///D:/Notiz/app/src/main/java/com/sayeed_dev/notiz/model/Note.kt)
`Note` data class-ke Room **Entity**-te convert kora hbe jate database eta "Table" hisebe chinhte pare.

#### [NEW] [NoteDao.kt](file:///D:/Notiz/app/src/main/java/com/sayeed_dev/notiz/data/NoteDao.kt)
Database-er sathe kotha bolar jonno Commands (Query, Insert, Delete) toiri kora hbe.

#### [NEW] [NoteDatabase.kt](file:///D:/Notiz/app/src/main/java/com/sayeed_dev/notiz/data/NoteDatabase.kt)
Puro database setup handle korar jonno main class toiri kora hbe.

#### [MODIFY] [HomeScreen.kt](file:///D:/Notiz/app/src/main/java/com/sayeed_dev/notiz/ui/screen/HomeScreen.kt)
Dummy data (List) bad diye database theke real notes load korar logic add kora hbe.

---

## Verification Plan

### Manual Verification
1. App run kore notun note add korun.
2. Home screen-e note-ta dekhachhe কিনা check korun.
3. App puro bondho (Force close) kore abar open korun. Check korun note-ta permanent-vabe save ache কিনা.

Alhamdulillah. Apni ki ready app-ke permanent data support deyar jonno? Approved korle amra dependency add kora shuru korbo insha allah.
