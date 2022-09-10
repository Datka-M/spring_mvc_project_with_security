package peaksoft.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Lesson;
import peaksoft.entity.Task;
import peaksoft.entity.Video;
import peaksoft.repository.LessonRepository;
import peaksoft.repository.VideoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final LessonRepository lessonRepository;
    public void saveVideoByLessonId(Long id, Video video) {
        Lesson lesson = lessonRepository.getById(id);
        lesson.setVideo(video);
        video.setLesson(lesson);
        videoRepository.save(video);
    }
    public void deleteVideo(Long id) {
        Video video = videoRepository.getById(id);
        video.setLesson(null);
        videoRepository.delete(video);
    }

    public void updateVideoById(Long id, Video video) {
        Video video1 = videoRepository.getById(id);
        video1.setVideoName(video.getVideoName());
        video1.setLink(video.getLink());
        videoRepository.save(video1);
    }

    public Video getVideoById(Long id) {
        return videoRepository.getById(id);
    }

    public List<Video> getAllByLessonId(Long id) {
        return videoRepository.getAllByLessonId(id);
    }
}
