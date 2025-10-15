# 🧭 Disto Application

**프로젝트명:** Disto Application  
**내용:** 배관용 파이프의 규격과 재질을 측정할 수 있도록 제작된 앱  

---

## 📅 세부 사항
- **프로젝트 기간:** 2025.09.15 ~ 2025.10.31  
- **기획 & 디자인:** 2025.09.16 ~ 2025.09.26
- **코드 구현:** 2025.09.29 ~ 2025.10.09
- **테스트 및 미비 사항 동작 구현:** 2025.10.09 ~ 

---

## ⚙️ 추가 사항
- 해상도 별 레이아웃 처리 완료 (기준: 600dp)

---

## 🏠 메인화면
> - 메인화면에서는 다음과 같이 전체 메뉴 버튼이 구현되어 있습니다.
> - UI 구현 : XML, Material
<img src="https://github.com/user-attachments/assets/2f7c51dd-59ed-48e9-a2ca-f9c50f55f732" width="300" />

---

## 📁 프로젝트 생성하기
> - 프로젝트 생성하기 화면에서는 프로젝트 단위로 측정 데이터를 관리할 수 있도록 구현 예정입니다.
> - A프로젝트 구현 시, A프로젝트에 저장되어있는 측정 데이터에 대해서 관리할 수 있도록 합니다.
> - 저장 방법 : Room DB 사용
<img src="https://github.com/user-attachments/assets/2f7c51dd-59ed-48e9-a2ca-f9c50f55f732" width="300" />
<img src="https://github.com/user-attachments/assets/8394bbe5-bbae-4ee9-a75a-58dafb5e9ed4" width="300" />

---

## 📂 프로젝트 선택하기
> - 프로젝트 선택하기 화면에서는 현재 생성되어있는 모든 프로젝트 목록들을 확인할 수 있습니다.
> - 프로젝트 생성하기 화면에서 신규 추가된 내용들은 이곳에서 목록을 확인할 수 있습니다.
> - 프로젝트 선택 시에는 선택된 프로젝트를 기반으로 저장되어 있는 데이터를 불러오거나 신규 측정 데이터를 추가할 수 있습니다.
> - 현재로는 목록만 확인이 가능하지만, 프로젝트 선택 시 선택된 프로젝트에 저장되어있는 측정 데이터들을 볼 수 있도록 구현 예정입니다.

<p>
  <img src="https://github.com/user-attachments/assets/2f7c51dd-59ed-48e9-a2ca-f9c50f55f732" width="300" />
  <img src="https://github.com/user-attachments/assets/2fa94094-3ff0-4ca3-9f03-e85e8f3d3b65" width="300" />
  <img src="https://github.com/user-attachments/assets/67f06e75-cdd3-4a41-b34f-d98cbf6e71b7" width="300" />
</p>

---

## 🔗 Bluetooth 연결하기
> - Leica사에서 제공하는 SDK를 활용해서 측량 데이터를 화면에 표시할 수 있도록 구현했습니다.
> - Leica사에서 제공하는 SDK 내용중 블루투스는 BLE 방식을 사용합니다.
> **Disto 장비 연결 (Leica SDK 활용)**
<img src="https://github.com/user-attachments/assets/2f7c51dd-59ed-48e9-a2ca-f9c50f55f732" width="300" />
<img src="https://github.com/user-attachments/assets/4ffe02c0-209e-4462-873c-ba0addc49e92" width="300" />

---

## 📏 측정하기
> - 측정에 필요한 데이터 입력 및 측정 진행  
> - **배관 타입:** Spinner 사용  
> - **관경:** 장비에서 측정된 데이터를 실시간 표시  
> - **Picture:** 카메라 앱 실행 및 촬영 (관 재질 촬영 목적)  
> - **초기화:** 모든 데이터 값 초기화  
> - **뒤로가기:** 이전 화면으로 이동  
> - **측정 결과 확인:** Fragment Popup에 표시 및 저장  
> - **저장 방식:** Room DB  
<img src="https://github.com/user-attachments/assets/2f7c51dd-59ed-48e9-a2ca-f9c50f55f732" width="300" />
<img src="https://github.com/user-attachments/assets/de41862e-b258-498e-a943-7cc51b91ca52" width="300" />
<img src="https://github.com/user-attachments/assets/0836e973-d67a-4edf-9293-1a1a3c11cfc5" width="300" />

## 💾 측정 저장을 위한 최종 확인
## ✅ 측정 저장 완료

> - 측정 데이터 저장을 위해서 최종 확인이 진행되는 화면입니다.
> - 도엽번호와 배관 갯수, 측정 진행한 관경, 수기 입력 값과 재질을 입력할 수 있습니다.
> - 관경 측정 버튼 클릭 시 Disto 장비에서 관경 측정이 진행됩니다. 측정이 된 최대값이 관경 측정 값과 측정값 확정 사이에 Input Box에 자동으로 입력이 되게 됩니다.
> - 측정값 확정 버튼을 누르면 첫번째부터 순차적으로 확정된 데이터 값이 입력이 옮겨지게 됩니다.
> - 측정이 완료된 데이터는 저장 버튼을 통해서 Room DB에 저장이 진행됩니다.
> - 저장 방식 : Room DB

<img src="https://github.com/user-attachments/assets/c829e689-3db4-483a-a565-38d9adf5a913" width="300" />



---

## 💾 측정 저장을 위한 최종 확인
## ✅ 측정 저장 완료



---

## 📂 저장된 데이터 리스트 확인하기
> - 저장된 데이터는 아래 화면에서 확인할 수 있습니다.
> - RecyclerView를 사용하여 Room DB에 저장되어 있는 데이터들을 불러와서 저장된 데이터들을 확인할 수 있습니다.
> - Room DB에 저장되어 있는 데이터 중에 선택적으로 삭제하는 기능이 추가되어 있습니다.
> - 내보내기 기능은 xlsx 파일로 내보내질 예정이며 추후 구현 예정으로 되어 있습니다.
<img src="https://github.com/user-attachments/assets/2f7c51dd-59ed-48e9-a2ca-f9c50f55f732" width="300" />
<img src="https://github.com/user-attachments/assets/55b18264-1bd5-4773-85d9-366e2a9c42e5" width="300" />
<img src="https://github.com/user-attachments/assets/504c1cb6-a026-4bb4-9ea0-2f1ac23f64cb" width="300" />


---

