
## Gereksinimler

Aşağıdaki docker kurulumlarından emin olunmalıdır.
- [Docker](https://www.docker.com/products/docker-desktop) 
- [Docker Compose](https://docs.docker.com/compose/) 

---

## Çalıştırma

Projeyi çalıştırmak için aşağıdaki adımları takip edin:

1. Proje dosyasını klonlayın:
   ```bash
   git clone https://github.com/husrevasici/CourierTracking.git
   
   ```

2. Uygulamanın Docker container'larını oluşturmak ve başlatmak için aşağıdaki komutu çalıştırın:
   ```bash
   docker compose up --build
   ```

   - `--build`: Uygulamanın `Dockerfile`'ını yeniden oluşturur.

3. Başarılı bir kurulum ve çalıştırma sonrası:
   - Uygulama: [http://localhost:8080](http://localhost:8080)
   - Veritabanı (PostgreSQL): [localhost:5432](localhost:5432)
örnek postman collection: [Uploading migros.postman_collection.json…]()

---
