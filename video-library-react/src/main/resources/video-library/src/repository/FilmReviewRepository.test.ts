import fetchMockJest from 'fetch-mock-jest';
import { filmReviewRepository } from './FilmReviewRepository';
import { deepStrictEqual } from 'assert';
import { existentFilmReview, insertFilmReview } from '../mock/FilmReviewMock';

describe('FilmReviewRepository', () => {
	const FILM_ID = 1;

	const repository = filmReviewRepository('jpa');

	test('update', async () => {
		fetchMockJest.postOnce(
			(url: string, opts: Request | RequestInit) => {
				deepStrictEqual(JSON.parse(opts.body!.toString()), existentFilmReview);
				return url === `/jpa/review/${FILM_ID}`;
			},
			existentFilmReview
		);

		await repository.update(existentFilmReview, FILM_ID)
			.then(it => expect(it).toStrictEqual(existentFilmReview));
	});

	test('insert', async () => {
		fetchMockJest.putOnce(
			(url: string, opts: Request | RequestInit) => {
				deepStrictEqual(JSON.parse(opts.body!.toString()), insertFilmReview);
				return url === `/jpa/review/${FILM_ID}`;
			},
			existentFilmReview
		);

		await repository.insert(insertFilmReview, FILM_ID)
			.then(it => expect(it).toStrictEqual(existentFilmReview));
	});
});