# Movie-Match KMP

A Kotlin Multiplatform application built with Jetpack Compose that helps users discover and match movies for shared viewing experiences. The app supports both Android and iOS platforms with shared business logic and UI components.

## 🎯 Project Overview

Movie-Match is designed to solve the common problem of choosing what to watch together. Users can create or join matching sessions, swipe through movie recommendations, and find movies that everyone wants to watch.

## ✨ MVP Features (Keep It Simple)

### 🔐 User Identification
- **UUID Assignment**: Simple UUID for user identification (no auth required)

### 🎬 Basic Movie Matching
- **TMDB Integration**: Fetch popular movies from TMDB API
- **Swipe Interface**: Simple left/right swipe for like/dislike
- **Basic Movie Info**: Show title, poster, and year only

### 👥 Simple Sessions
- **Create Session**: Generate session with simple code
- **Join Session**: Enter session code to join
- **Real-time Sync**: Firebase for live user choices

### 🤝 Basic Matching
- **Find Matches**: Show movies liked by all participants
- **Simple Results**: List matched movies with basic info

## 🏗️ Technical Architecture

### Multi-Module Architecture
```
Movie-Match-KMP/
├── composeApp/                 # Main application module
│   ├── commonMain/            # Shared code across platforms
│   ├── androidMain/           # Android-specific implementations
│   └── iosMain/               # iOS-specific implementations
├── core/
│   ├── auth/                  # Authentication module
│   │   ├── commonMain/        # Auth interfaces & models
│   │   ├── androidMain/       # Firebase Auth Android
│   │   └── iosMain/           # Firebase Auth iOS
│   ├── network/               # Network and API handling
│   ├── database/              # Real-time & local data storage
│   │   ├── commonMain/        # Repository interfaces & models
│   │   │   ├── repository/    # Repository abstractions
│   │   │   ├── model/         # Data models
│   │   │   └── datasource/    # DataSource interfaces
│   │   ├── androidMain/       # Firebase Android implementation
│   │   └── iosMain/           # Firebase iOS implementation
│   └── common/                # Shared utilities and models
├── feature/
│   ├── matching/              # Movie matching functionality
│   ├── session/               # Session management & real-time sync
│   ├── profile/               # User profile management
│   └── movies/                # Movie browsing and details
├── design-system/             # UI components and theming
└── shared/                    # Shared business logic
```

### Technology Stack (MVP)
- **Framework**: Kotlin Multiplatform with Compose Multiplatform
- **UI**: Jetpack Compose for Android & iOS
- **Architecture**: Simple MVVM
- **Networking**: Ktor client for TMDB API
- **Real-time**: Firebase Realtime Database (expect/actual)
- **Image Loading**: Coil for movie posters
- **Navigation**: Basic Compose Navigation
- **State Management**: Compose State + ViewModel

## 🔥 Simple Firebase Setup

### Real-time Database (MVP)
- Use Firebase Realtime Database for session synchronization
- expect/actual pattern for platform-specific implementations
- Simple data structure: sessions/{sessionId}/users/{userId}/choices
- Basic real-time updates for user choices

## 🚀 MVP Development Plan

### Phase 1: Basic Setup (Week 1)
- [ ] Set up KMP project structure
- [ ] Configure Firebase for Android & iOS
- [ ] Create basic navigation (3 screens: Home, Session, Results)
- [ ] Implement UUID user identification

### Phase 2: Core Features (Week 2)
- [ ] Integrate TMDB API for popular movies
- [ ] Create simple swipe interface
- [ ] Implement Firebase expect/actual for sessions
- [ ] Build session creation/joining

### Phase 3: Real-time Matching (Week 3)
- [ ] Real-time user choice synchronization
- [ ] Basic matching algorithm (find common likes)
- [ ] Results screen showing matched movies
- [ ] Test on both Android and iOS

## 🛠️ Quick Setup

### Prerequisites
- Android Studio
- Xcode (for iOS)
- JDK 17+

### Getting Started
1. Clone the repository
2. Open in Android Studio
3. Add `google-services.json` (Android) and `GoogleService-Info.plist` (iOS)
4. Create `local.properties`:
```properties
TMDB_API_KEY=your_tmdb_api_key
```
5. Run on Android or iOS

## 📋 MVP Requirements

### What the app does:
- Create/join sessions with simple codes
- Swipe through popular movies (like/dislike)
- Show movies that everyone liked
- Real-time sync across devices

### Target:
- Android 7.0+ and iOS 13.0+
- Support 2-4 users per session
- Basic UI with smooth animations

---

**Focus**: Keep it simple, get it working on both platforms first, then iterate.
