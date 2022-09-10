package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Video;
import peaksoft.service.VideoService;

@Controller
public class VideoController {

    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/allVideos/{lessonId}")
    public String getAllVideos(Model model, @PathVariable("lessonId")Long lessonId){
        model.addAttribute("videos",videoService.getAllByLessonId(lessonId));
        model.addAttribute("lessonId",lessonId);
        return "/videos/mainVideoPage";
    }

    @GetMapping("/newVideo/{lesId}")
    public String addVideo(Model model,@PathVariable("lesId")Long lesId){
        model.addAttribute("newVideo",new Video());
        model.addAttribute("lesId",lesId);
        return "/videos/newVideo";
    }

    @PostMapping("/saveVideo/{lesId}")
    public String saveVideo(@PathVariable("lesId")Long lesId, @ModelAttribute("newVideo")Video video){
        videoService.saveVideoByLessonId(lesId,video);
        return "redirect:/allVideos/ "+lesId;
    }

    @GetMapping("/editVideo/{videoId}")
    public String editVideo(@PathVariable("videoId")Long videoId,Model model){
        Video video = videoService.getVideoById(videoId);
        model.addAttribute("video",video);
        model.addAttribute("lessonId2",video.getLesson().getId());
        return "/videos/updateVideo";
    }

    @PostMapping("/{videoId}/{lessonId2}/updateVideo")
    public String updateVideo(@PathVariable("videoId")Long videoId,
                              @PathVariable("lessonId2")Long lessonId2,
                              @ModelAttribute("video")Video video){
        videoService.updateVideoById(videoId,video);
        return "redirect:/allVideos/ "+lessonId2;
    }

    @RequestMapping("/deleteVideo/{id}/{lessonId}")
    public String deleteVideo(@PathVariable("id")Long id,
                              @PathVariable("lessonId")Long lessonId){
        videoService.deleteVideo(id);
        return "redirect:/allVideos/ "+lessonId;
    }


}
