package com.josephidowu;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoftwareEngineerService {
    private final SoftwareEngineerRepository softwareEngineerRepository;
    private final AIService aiService;

    public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository, AIService aiService) {
        this.softwareEngineerRepository = softwareEngineerRepository;
        this.aiService = aiService;
    }

    public void insertSoftwareEngineer(SoftwareEngineer softwareEngineer) {
        String prompt = """
                Based on the programming tech stack that %s that %s has given, provide a full learning path and recommendations for this person.
                """.formatted(softwareEngineer.getTechStack(), softwareEngineer.getName());
        String chatRes = aiService.chat(prompt);

        softwareEngineer.setLearningPathRecommendation(chatRes);

        softwareEngineerRepository.save(softwareEngineer);
    }

    public List<SoftwareEngineer> getAllSoftwareEngineers() {
        return  softwareEngineerRepository.findAll();
    }

    public SoftwareEngineer getSoftwareEngineerById(Integer id) {
        return softwareEngineerRepository.findById(id).orElseThrow(() -> new IllegalStateException(id + " not found"));
    }

    public void deleteSoftwareEngineerById(Integer id) {
        softwareEngineerRepository.deleteById(id);
    }

    public void updateSoftwareEngineerById(Integer id, SoftwareEngineer updatedSoftwareEngineer) {
        SoftwareEngineer softwareEngineer = softwareEngineerRepository.findById(id).orElseThrow(() -> new IllegalStateException(id + " not found"));

        softwareEngineer.setName(updatedSoftwareEngineer.getName());
        softwareEngineer.setTechStack(updatedSoftwareEngineer.getTechStack());

        softwareEngineerRepository.save(softwareEngineer);
    }
}
