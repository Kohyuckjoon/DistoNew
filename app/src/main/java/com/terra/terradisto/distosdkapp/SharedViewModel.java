package com.terra.terradisto.distosdkapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    /**
     * 현재 선택된 프로젝트 ID를 저장하는 LiveData
     * 기본값은 -1로 설정 (프로젝트가 선택되지 않음 = 전체 데이터)
     */
    private final MutableLiveData<Integer> selectedProjectId = new MutableLiveData<>(-1);

    // 선택된 프로젝트 이름을 저장하는 LiveData
    private final MutableLiveData<String> selectedProjectName = new MutableLiveData<>();

    // 프로젝트 ID를 LiveData로 반환 (읽기 전용)
    public LiveData<Integer> getSelectedProjectId() {
        return selectedProjectId;
    }

    // 새로운 프로젝트 ID를 설정
    public void setProjectId(int id) {
        selectedProjectId.setValue(id);
    }

    // 프로젝트 이름을 LiveData로 반환 (읽기 전용)
    public LiveData<String> getSelectedProjectName() {
        return selectedProjectName;
    }

    // 새로운 프로젝트 이름을 설정
    public void setSeletedProjectName(String name) {
        selectedProjectName.setValue(name);
    }
}
