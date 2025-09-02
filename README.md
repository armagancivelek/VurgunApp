# Vurgun - Spor Bahis Uygulaması

Vurgun, Android platformu için geliştirilmiş modern bir spor bahis uygulamasıdır. Jetpack Compose ve modern Android geliştirme prensipleri kullanılarak geliştirilmiştir.

## 🏗️ Mimari

Uygulama Clean Architecture prensiplerine dayanan modüler bir yapıya sahiptir:

### Core Modülleri
- **core/common** - Paylaşılan modeller, yardımcı sınıflar ve temel işlevsellik
- **core/common-ui** - UI bileşenleri, tema ve tasarım sistemi
- **core/data** - Veri katmanı, DataStore ve veri yönetimi
- **core/domain** - İş mantığı, use case'ler ve domain modelleri
- **core/network** - Ağ işlemleri, interceptor'lar ve API yönetimi
- **core/home-api** - Ana sayfa API servisleri

### Feature Modülleri
- **feature/home** - Ana sayfa ve spor etkinlikleri
- **feature/slips** - Oynanmış kupon geçmişi
- **feature/current-slip** - Aktif kupon yönetimi

## 🚀 Özellikler

- 📱 Modern Material 3 tasarımı
- 🏈 Çoklu spor kategorisi desteği
- 🎯 Bahis kuponu oluşturma ve yönetimi
- 📊 Canlı oranlar ve istatistikler
- 💰 Bakiye kontrolü ve kupon takibi
- 🔍 Spor ve maç arama
- 📡 Offline durum takibi

## 🛠️ Teknoloji Stack

- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM + Clean Architecture
- **Dependency Injection**: Hilt/Dagger
- **Navigation**: Jetpack Navigation Compose
- **Network**: Retrofit + OkHttp
- **Local Storage**: DataStore
- **Analytics**: Firebase Analytics
- **Testing**: JUnit, Espresso, Roborazzi

## 📋 Gereksinimler

- Android API 24+ (Android 7.0)
- Kotlin 2.1.0
- Android Gradle Plugin 8.11.1


## 🔧 Geliştirme

### Code Quality Tools
- **Ktlint** - Kod formatı kontrolü
- **Detekt** - Statik kod analizi
- **Git Hooks** - Pre-commit kontrolleri

### Convention Plugins (Build Logic)

Proje genelinde ortak Gradle yapılandırmalarını yönetmek için build-logic modülü kullanılmıştır. Bu sayede:

Ortak bağımlılıklar merkezi olarak yönetilir

Tüm modüllerde aynı Gradle ayarları ve pluginler uygulanır

Kod tekrarı azaltılır ve build süreleri optimize edilir

Örnek kullanım:

...
// app/build.gradle.kts
plugins {
    id("vurgun.android.application")
    id("vurgun.android.application.compose")
    id("vurgun.android.hilt")
}
...

build-logic içinde:

android-convention.gradle.kts → Android uygulama/library ortak ayarları

compose-convention.gradle.kts → Compose yapılandırmaları

hilt-convention.gradle.kts → Hilt DI setup

Yeni bir modül eklediğinde sadece ilgili plugin’i uygulamak yeterlidir.

### Kod formatını kontrol et:
```bash
./gradlew ktlintCheck
```

### Kod formatını düzelt:
```bash
./gradlew ktlintFormat
```

## 📁 Proje Yapısı

```
Vurgun/
├── app/                    # Ana uygulama modülü
├── core/                   # Çekirdek modüller
│   ├── common/            # Paylaşılan kod
│   ├── common-ui/         # UI bileşenleri
│   ├── data/              # Veri katmanı
│   ├── domain/            # Domain katmanı
│   ├── network/           # Ağ katmanı
│   └── home-api/          # API servisleri
├── feature/               # Özellik modülleri
│   ├── home/              # Ana sayfa
│   ├── slips/             # Kupon geçmişi
│   └── current-slip/      # Aktif kupon
├── build-logic/           # Gradle convention plugins
└── config/                # Yapılandırma dosyaları
```

## 🎯 Ana Özellikler

### Ana Sayfa
- Spor kategorilerini görüntüleme
- Maç arama ve filtreleme
- Oranları görüntüleme ve seçim

### Kupon Yönetimi
- Bahis seçimi ve kupon oluşturma
- Kupon bedeli girişi
- Tahmini kazanç hesaplama
- Kupon oynatma

### Kupon Geçmişi
- Oynanmış kuponları görüntüleme
- Kupon detayları

## 🔒 Güvenlik
- Ağ trafiği güvenli bağlantılar üzerinden yapılır
- Kullanıcı verileri şifrelenerek saklanır


