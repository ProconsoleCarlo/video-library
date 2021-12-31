import { Review } from '../model/Film';
import { Protocol } from '../model/Protocol';
import { fetchHttpClient } from '../utils/HttpClient';
import { ByProtocolRepository } from './ByProtocolRepository';

export interface FilmReviewRepository extends ByProtocolRepository {
  update(review: Review, filmId: Number): Promise<Review>;

  insert(review: Review, filmId: Number): Promise<Review>;
}

const httpClient = fetchHttpClient<Review, Review>();

export const filmReviewRepository = (protocol: Protocol): FilmReviewRepository => {
  return {
    protocol(): Protocol {
      return protocol;
    },
    update(review: Review, filmId: Number): Promise<Review> {
      return httpClient.post({url: `/${protocol}/review/${filmId}`, body: review});
    },
    insert(review: Review, filmId: Number): Promise<Review> {
      return httpClient.put({url: `/${protocol}/review/${filmId}`, body: review});
    }
  };
};