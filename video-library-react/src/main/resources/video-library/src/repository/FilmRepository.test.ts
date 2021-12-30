import { filmRepository } from './FilmRepository';
import fetchMockJest from 'fetch-mock-jest';
import { deepStrictEqual } from 'assert';
import { existentFilms, insertFilms } from '../mock/FilmsMock';

describe('FilmRepository', () => {
	const repository = filmRepository('jpa');

	test('get', async () => {
		fetchMockJest.getOnce(
			(url: string) => url === `/jpa/films`,
			existentFilms
		);

		await repository.get()
			.then(it => expect(it).toStrictEqual(existentFilms));
	});

	test('update', async () => {
		fetchMockJest.postOnce(
			(url: string, opts: Request | RequestInit) => {
				deepStrictEqual(JSON.parse(opts.body!.toString()), existentFilms);
				return url === `/jpa/films`;
			},
			existentFilms
		);

		await repository.update(existentFilms)
			.then(it => expect(it).toStrictEqual(existentFilms));
	});

	test('insert', async () => {
		fetchMockJest.putOnce(
			(url: string, opts: Request | RequestInit) => {
				deepStrictEqual(JSON.parse(opts.body!.toString()), insertFilms);
				return url === `/jpa/films`;
			},
			existentFilms
		);

		await repository.insert(insertFilms)
			.then(it => expect(it).toStrictEqual(existentFilms));
	});
});