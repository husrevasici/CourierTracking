
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
örnek postman collection: https://galactic-trinity-161139.postman.co/workspace/locali%C5%9F~af57d6dd-ea8a-4d20-aa6b-f85a3d15607e/collection/23533165-c6986d51-d3a3-4890-a719-4bfd781450b3?action=share&creator=23533165

---
