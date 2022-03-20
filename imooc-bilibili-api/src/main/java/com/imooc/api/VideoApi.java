package com.imooc.api;

import com.imooc.api.support.UserSupport;
import com.imooc.bilibili.domain.JsonResponse;
import com.imooc.bilibili.domain.PageResult;
import com.imooc.bilibili.domain.Video;
import com.imooc.bilibili.service.UserFollowingService;
import com.imooc.bilibili.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: TODO 类描述
 * @author: luf
 * @date: 2022/3/20
 **/
@RestController
public class VideoApi {

    @Autowired
    private VideoService videoService;
    @Autowired
    private UserSupport userSupport;

    /**
     * 视频投稿
     */
    @PostMapping("/videos")
    public JsonResponse<String> addVideos(@RequestBody Video video) {
        Long userId = userSupport.getCurrentUserId();
        video.setUserId(userId);
        videoService.addVideos(video);
        //在es中添加一条视频
        // elasticSearchService.addVideo(video)
        return JsonResponse.success();
    }

    /**
     * （分区内）分页查询视频列表-瀑布流
     * 首页中，每个分区都先查询部分数据。
     */
    @GetMapping("/video")
    public JsonResponse<PageResult<Video>> pageListVideos(Integer size, Integer no, String area) {
        PageResult<Video> result = videoService.pageListVideos(size, no, area);
        return new JsonResponse<>(result);
    }


}
