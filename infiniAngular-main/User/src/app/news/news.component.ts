import { Component } from '@angular/core';
import { NewsService } from '../Services/news.service';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent {
  teslaNews: any[] = [];

  constructor(private newsService: NewsService) { }

  ngOnInit(): void {
    const fromDate = this.getFormattedDate(new Date('2024-05-10'));
    this.getfinanceNews(fromDate);
  }

  getfinanceNews(fromDate: string): void {
    this.newsService.getNews(fromDate)
      .subscribe(articles => {
        this.teslaNews = articles;
      });
  }

  private getFormattedDate(date: Date): string {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
  }
}
