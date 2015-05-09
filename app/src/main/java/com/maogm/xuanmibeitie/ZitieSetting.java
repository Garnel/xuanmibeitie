package com.maogm.xuanmibeitie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maoguangming on 5/9/15.
 */

/*
{
    "total": 4,
    "name": "玄秘碑帖",
    "imageDir": "xuanmibeitie",
    "images": [
    {
        "image": "1.jpg",
            "word": "唐故左街僧录"
    }
    ]
}
*/
public class ZitieSetting {
    int total;
    String name;
    String imageDir;
    ArrayList<ZitieImageItem> images;

    class ZitieImageItem {
        String image;
        String word;
    }
}
