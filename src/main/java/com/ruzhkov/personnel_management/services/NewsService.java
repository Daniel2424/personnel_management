package com.ruzhkov.personnel_management.services;

import com.ruzhkov.personnel_management.entity.News;
import com.ruzhkov.personnel_management.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class NewsService {
    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Transactional
    public void save(News news){
        newsRepository.save(news);
    }

    public News getNewsById(int id){
        return newsRepository.findById(id).get();
    }

    public List<News> getAllNews(){
        List<News> list = newsRepository.findAll();
        Collections.reverse(list);
        return list;
    }
    public List<News> getSomeNews(){
        List<News> list = newsRepository.findAll();
        Collections.reverse(list);

        int len = list.size();
        while(len > 6){
            len--;
        }
        return list.subList(0, len);
    }

    @Transactional
    public void deleteNewsById(int id){
        newsRepository.deleteById(id);
    }

    @Transactional
    public void updateById(int id, News updatedNews){
        updatedNews.setId(id);
        newsRepository.save(updatedNews);
    }


}
