# Real Persistence: Room Database & ViewModel Integration

Bismillah.

Alhamdulillah Buddy, amra amader app-er UI (Welcome, Login, SignUp, Home, Add/Edit) purapuri "Lock" kore phelechi. Ekhon amader app-ta dekhte ekdom professional, kintu seta ekhno "Real" na.

**Keno?** Karun amra ekhono dummy data use korchi aar "Save" button click korle kichu asholei save hochche na.

Ekjon Senior Developer-er moto ekhon amader ashol target hobe app-ke **"Live"** kora. Er jonno amra dui-ti boro jinis shikhbo:
1.  **Room Database:** Note gulo phone-e permanent save korar jonno.
2.  **ViewModel:** UI theke logic-ke alada korar jonno (Professional Standard).

## Proposed Changes

### [Component Name] - Data Architecture

#### [MODIFY] [libs.versions.toml](file:///D:/Notiz/gradle/libs.versions.toml) & [build.gradle.kts](file:///D:/Notiz/app/build.gradle.kts)
- Android-er official **Room Database** library add kora hbe.
- **KSP** (Kotlin Symbol Processing) setup kora hbe jeta modern professional standard.

#### [MODIFY] [Note.kt](file:///D:/Notiz/app/src/main/java/com/sayeed_dev/notiz/model/Note.kt)
- `Note` class-ke Room **Entity**-te convert kora hbe (Table toiri kora).

#### [NEW] [NoteDao.kt](file:///D:/Notiz/app/src/main/java/com/sayeed_dev/notiz/data/NoteDao.kt) & [NoteDatabase.kt](file:///D:/Notiz/app/src/main/java/com/sayeed_dev/notiz/data/NoteDatabase.kt)
- Database-er sathe kotha bolar command list (DAO) toiri hbe.

#### [NEW] [NoteViewModel.kt](file:///D:/Notiz/app/src/main/java/com/sayeed_dev/notiz/ui/viewmodel/NoteViewModel.kt)
- Shob data management (Save, Delete, Load) ekhane hbe.

---

## Verification Plan

### Manual Verification
- Notun note likhe "Save" click korle seta Grid-e chole ashbe (No dummy data anymore!).
- Note delete korle seta Grid theke muche jabe.
- App puro bondho kore abar khulle dekha jabe note gulo thikmoto ache.

Alhamdulillah. Apni ki apnar app-ke ekta "Real Life" app-e convert korar jonno ready? Approved korle amra dependency setup theke shuru korbo insha allah.
