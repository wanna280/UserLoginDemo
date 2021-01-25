package com.example.demopro.controller.Blog;

import com.example.demopro.service.BlogLogoItemsService;
import com.example.demopro.service.Impl.BlogLogoItemsServiceImpl;
import com.example.demopro.service.Impl.RedisServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BlogLogoItemsController {
    @Resource
    BlogLogoItemsServiceImpl blogLogoItemsService;

    @Resource
    RedisServiceImpl redisService;

    @RequestMapping(value = "/logoItems/get/{id}", method = RequestMethod.GET)
    public Object GetLogoItems(@PathVariable int id) {
        return blogLogoItemsService.GetBlogItems(id);
    }

    @RequestMapping(value = "/logoItems/comments/decreasing/{id}")
    public Map<String, Object> SubCommentNumbers(@PathVariable int id) {
        Map<String, Object> map = new HashMap<>();
        blogLogoItemsService.DecreasingCommentNumbers(id);

        map.put("status", true);
        map.put("msg", "减少成功");
        return map;
    }

    @RequestMapping(value = "/logoItems/comments/increasing/{id}")
    public Map<String, Object> AddCommentNumbers(@PathVariable int id) {
        Map<String, Object> map = new HashMap<>();
        blogLogoItemsService.IncreasingCommentNumbers(id);

        map.put("status", true);
        map.put("msg", "增加成功");
        return map;
    }

    @RequestMapping(value = "/logoItems/thumbsup/decreasing/{id}")
    public Map<String, Object> SubThumbsUpNumbers(@PathVariable int id) {
        Map<String, Object> map = new HashMap<>();
        blogLogoItemsService.DecreasingThumbsUpNumbers(id);

        map.put("status", true);
        map.put("msg", "增加成功");
        return map;
    }

    @RequestMapping(value = "/logoItems/thumbsup/increasing/{id}")
    public Map<String, Object> AddThumbsUpNumbers(@PathVariable int id) {
        Map<String, Object> map = new HashMap<>();
        blogLogoItemsService.IncreasingThumbsUpNumbers(id);

        map.put("status", true);
        map.put("msg", "增加成功");
        return map;
    }


    @RequestMapping(value = "/logoItems/thumbsup/isthumbsup/{id}")
    public Map<String, Object> isThumbsUp(@PathVariable int id) {
        Map<String, Object> map = new HashMap<>();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String key = "isthumbsup_" + username + "_" + id;  //生成key
        String value = redisService.get(key);
        //如果Redis中有，那么返回true，否则返回false
        if (value == null || value.equals("")) {
            map.put("status", false);
        } else {
            map.put("status", true);
        }
        return map;
    }

    @RequestMapping(value = "/logoItems/thumbsup/thumbsup/{id}")
    public Map<String, Object> ThumbsUp(@PathVariable int id) {
        Map<String, Object> map = new HashMap<>();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String key = "isthumbsup_" + username + "_" + id;  //生成key
        blogLogoItemsService.IncreasingThumbsUpNumbers(id);
        redisService.set(key, "yes", 60 * 60 * 24); //key有效期为1day
        map.put("status", true);
        return map;
    }

    @RequestMapping(value = "/logoItems/watchings/decreasing/{id}")
    public Map<String, Object> SubWatchingNumbers(@PathVariable int id) {
        Map<String, Object> map = new HashMap<>();
        blogLogoItemsService.DecreasingWatchingNumbers(id);

        map.put("status", true);
        map.put("msg", "增加成功");
        return map;
    }

    @RequestMapping(value = "/logoItems/watchings/increasing/{id}")
    public Map<String, Object> AddWatchingNumbers(@PathVariable int id) {
        Map<String, Object> map = new HashMap<>();
        blogLogoItemsService.IncreasingWatchingNumbers(id);

        map.put("status", true);
        map.put("msg", "增加成功");
        return map;
    }

}
