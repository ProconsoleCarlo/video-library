import { Review } from '../model/Film';
import { fetchHttpClient } from '../utils/HttpClient';
import { NewReview } from '../model/NewFilm';

export interface FilmReviewRepository {
	update(review: Review, filmId: Number): Promise<Review>;

	insert(review: NewReview, filmId: Number): Promise<Review>;
}

const httpClient = fetchHttpClient<string, Review>();

export const filmReviewRepository = (protocol: String): FilmReviewRepository => {
	return {
		update(review: Review, filmId: Number): Promise<Review> {
			return httpClient.post({url: `/${protocol}/review/${filmId}`, body: JSON.stringify(review)});
		},
		insert(review: NewReview, filmId: Number): Promise<Review> {
			return httpClient.put({url: `/${protocol}/review/${filmId}`, body: JSON.stringify(review)});
		}
	};
};