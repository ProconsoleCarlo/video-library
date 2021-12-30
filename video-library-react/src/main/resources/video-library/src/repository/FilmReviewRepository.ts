import { Review } from '../model/Film';
import { Protocol } from '../model/Protocol';
import { fetchHttpClient } from '../utils/HttpClient';

export interface FilmReviewRepository {
  update(review: Review, filmId: Number): Promise<Review>;

  insert(review: Review, filmId: Number): Promise<Review>;
}

const httpClient = fetchHttpClient<string, Review>();

export const filmReviewRepository = (protocol: Protocol): FilmReviewRepository => {
  return {
    update(review: Review, filmId: Number): Promise<Review> {
      return httpClient.post({url: `/${protocol}/review/${filmId}`, body: JSON.stringify(review)});
    },
    insert(review: Review, filmId: Number): Promise<Review> {
      return httpClient.put({url: `/${protocol}/review/${filmId}`, body: JSON.stringify(review)});
    }
  };
};