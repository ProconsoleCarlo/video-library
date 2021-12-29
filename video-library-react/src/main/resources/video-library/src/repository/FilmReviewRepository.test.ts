import fetchMockJest from 'fetch-mock-jest';
import { Review } from '../model/Film';
import { filmReviewRepository } from './FilmReviewRepository';
import { NewReview } from '../model/NewFilm';
import { deepStrictEqual } from 'assert';

describe('FilmReviewRepository', () => {
	const FILM_ID = 1;

	const repository = filmReviewRepository('jpa');

	test('update', async () => {
		const review: Review = {
			'id': 1,
			'date': '2012-12-31T00:00:00',
			'rating': 8,
			'detail': 'This is a review'
		};
		fetchMockJest.postOnce(
			(url: string, opts: Request | RequestInit) => {
				deepStrictEqual(JSON.parse(opts.body!.toString()), review);
				return url === `/jpa/review/${FILM_ID}`;
			},
			review
		);

		await repository.update(review, FILM_ID)
			.then(it => expect(it).toStrictEqual(review));
	});

	test('insert', async () => {
		const review: NewReview = {
			'id': null,
			'date': '2012-12-31T00:00:00',
			'rating': 8,
			'detail': 'This is a review'
		};
		const savedReview: Review = {
			'id': 1,
			'date': '2012-12-31T00:00:00',
			'rating': 8,
			'detail': 'This is a review'
		};
		fetchMockJest.putOnce(
			(url: string, opts: Request | RequestInit) => {
				deepStrictEqual(JSON.parse(opts.body!.toString()), review);
				return url === `/jpa/review/${FILM_ID}`;
			},
			savedReview
		);

		await repository.insert(review, FILM_ID)
			.then(it => expect(it).toStrictEqual(savedReview));
	});
});