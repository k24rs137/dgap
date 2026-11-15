# D-Gap

D-Gapは、スマートフォン、SNS、Googleサービス、金融アプリなどの使い方を、やさしい手順で学べるデジタル学習Webアプリです。

高齢者、留学生、デジタル機器に不慣れな利用者を想定し、多言語表示、音声読み上げ、学習進捗、困りごと診断などを提供します。

## 公開URL

https://dgap-14r4.onrender.com

Renderの無料プランでは、一定時間アクセスがない場合、初回表示に時間がかかることがあります。

## 主な機能

- 4カテゴリ・17レッスン
- 日本語・韓国語・英語・ひらがな表示
- レッスン検索、クイズ、操作練習
- 音声読み上げ・音声入力
- 学習進捗・履歴・お気に入り
- キーワード式レッスン案内
- 困りごと診断、詐欺メール診断
- 会員登録・ログイン（変更不可のユーザーIDと変更可能なユーザー名を分離）
- 学習進捗・最近の学習・お気に入りを確認できるマイページ
- コミュニティ投稿、いいね、削除

## 技術構成

- Java 17
- Spring Boot 3.5
- Spring MVC / Thymeleaf
- Spring Data JPA
- Spring Security
- Flyway
- PostgreSQL（本番）
- H2（ローカル・テスト）
- Maven Wrapper
- Docker
- Render / Neon

## ディレクトリ構成

```text
src/main/
├── java/com/example/dgap/
│   ├── config/          Spring Security設定
│   ├── controller/      画面・APIコントローラー
│   ├── dto/             APIのリクエスト・レスポンス
│   ├── model/           ドメインモデル・JPAエンティティ
│   ├── repository/      JPAリポジトリ
│   └── service/         ユーザー処理・レッスンカタログ
└── resources/
    ├── content/         レッスン・翻訳データ
    ├── db/migration/    Flywayマイグレーション
    ├── static/
    │   ├── css/
    │   └── js/
    │       ├── common/
    │       └── features/
    └── templates/
        └── fragments/
```

レッスン内容と4言語の翻訳は、`src/main/resources/content/lesson-catalog.yaml`で管理します。

## 必要環境

- Java 17
- Git

Mavenの個別インストールは不要です。リポジトリに含まれるMaven Wrapperを使用します。

## ローカル起動

Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=local"
```

macOS / Linux:

```bash
bash ./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

起動後、次のURLへアクセスします。

```text
http://localhost:8081
```

ローカル環境では、`target/dgap-local.mv.db`にH2データベースが作成されます。

## テスト

Windows:

```powershell
.\mvnw.cmd test
```

macOS / Linux:

```bash
bash ./mvnw test
```

テストではH2のインメモリDBを使用し、Flywayマイグレーション、画面描画、認証、学習データ、コミュニティ、CSRF保護を確認します。

## 本番環境変数

```text
DATABASE_URL=jdbc:postgresql://HOST/DATABASE?sslmode=require
DB_USER=データベースユーザー
DB_PASSWORD=データベースパスワード
PORT=10000
SESSION_COOKIE_SECURE=true
```

接続情報やパスワードはGitへ保存しないでください。

## データベースマイグレーション

スキーマはFlywayで管理します。

```text
src/main/resources/db/migration/
└── V1__initial_schema.sql
```

Hibernateは`ddl-auto: validate`でスキーマを検証し、テーブルを自動変更しません。既存の本番DBへ初めて適用する前には、必ずバックアップを取得してください。

## セキュリティ

- BCryptによるパスワードハッシュ化
- HTTPセッション認証
- Spring SecurityによるAPI認可
- CSRFトークン検証
- Content Security Policyなどのレスポンスヘッダー
- 本番Cookieの`HttpOnly`、`Secure`、`SameSite=Lax`
- ログイン時のセッションID更新

## Docker

```bash
docker build -t dgap .
docker run --rm -p 8081:8081 \
  -e DATABASE_URL="jdbc:postgresql://HOST/DATABASE?sslmode=require" \
  -e DB_USER="USER" \
  -e DB_PASSWORD="PASSWORD" \
  -e PORT=8081 \
  dgap
```

Dockerfileはマルチステージ構成で、JDKイメージでビルドし、JREイメージで実行します。

## CI

GitHub Actionsで以下を自動確認します。

- Mavenテスト
- Dockerイメージのビルド
