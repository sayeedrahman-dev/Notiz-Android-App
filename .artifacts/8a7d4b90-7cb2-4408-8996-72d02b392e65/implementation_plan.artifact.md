# Notiz Professional Workflow & Roadmap (3 Days)

Bismillah.

Ekjon professional Android developer jokhon Figma design pay, ora sorasori code shuru kore na. Ora prothome kichu step follow kore jeta amra amader ei project-e follow korbo insha allah.

## Professional Developer's Workflow

1.  **Requirement Analysis**: Figma file-er shob screen dekhe bujhe neya (Koyta screen? Ek screen theke arek screen-e kibabe jabe?).
2.  **Asset Extraction**: Icons, images, ebong specific colors Figma theke neya.
3.  **Project Structure**: Folders gulo organize kora (e.g., `ui`, `data`, `navigation`).
4.  **Theming**: Figma-er color ebong font gulo app-er `Theme.kt` te define kora.
5.  **Navigation Setup**: App-er navigation graph banano.
6.  **Feature Implementation**: Ekta ekta feature (e.g., Auth, Home, Note) dore dore shesh kora.

---

## Professional 3-Day Roadmap for NOTIz

### Day 1: Foundation & Authentication
*   **Project Structure**: Folder setup (ui, navigation, model).
*   **Theming**: Figma color palette (e.g., `#F5F2E9` for background) app-e apply kora.
*   **Navigation**: `NavHost` setup kora (Welcome -> Login -> Sign Up).
*   **Screens**: Welcome, Login, ebong Sign Up screen Figma-er moto exact design kora.

### Day 2: Data & Home Experience
*   **Data Model**: Note-er jonno `Note` data class banano.
*   **Home Screen (States)**: Empty state (No notes yet) ebong Note list state design kora.
*   **Components**: Reusable card design kora (Note Card).
*   **Search UI**: Home screen-er search bar setup kora.

### Day 3: Note Operations & Polishing
*   **Add/Edit Note**: Note lekhar ebong edit korar screen.
*   **State Management**: Note save ebong delete korar logic (Simple List based).
*   **Final Polishing**: Figma-er protiti pixel check kore final touch deya.

---

## Day 1: Implementation Details

### Step 1: Project Organization [NEW]
Folder structure reorganize kora:
- `com.sayeed_dev.notiz.ui.navigation`
- `com.sayeed_dev.notiz.ui.theme`
- `com.sayeed_dev.notiz.model`

### Step 2: Custom Theme [MODIFY]
Figma theke neya exact colors (Bg: #F5F2E9, Text: #454B54) `Color.kt` ebong `Theme.kt` te set kora.

### Step 3: Auth Screens [MODIFY/NEW]
- **WelcomeScreen**: Figma icon/text layout exact kora.
- **LoginScreen**: Input fields refine kora.
- **SignUpScreen**: Figma onujayi notun screen banano.

---

## Verification Plan

### Manual Verification
- NavHost-er maddhome protiti screen-e thikvabe jawa jachhe kina.
- Custom colors gulo Figma-er moto thikvabe show korche kina.

Alhamdulillah.

