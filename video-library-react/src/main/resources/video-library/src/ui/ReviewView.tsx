import React, { useState } from 'react';
import { Review } from '../model/Film';
import { filmReviewRepositories } from '../repository/ProtocolRepository';

interface Props {
	filmId: number,
	reviews: Review[],
	rootPath: string
}

const EMPTY_REVIEW: Review = {
	id: null,
	date: '',
	rating: 6,
	detail: null
};

export const ReviewView: React.FC<Props> = ({filmId, reviews, rootPath}) => {
	const filmReviewRepository = filmReviewRepositories.get(rootPath);
	const [jsonReview, setJsonReview] = useState(EMPTY_REVIEW);
	const addReview = (): Promise<Review> => {
		return filmReviewRepository.insert(jsonReview, filmId);
	};
	const updateReview = (): Promise<Review> => {
		return filmReviewRepository.update(jsonReview, filmId);
	};

	const saveReview = (): void => {
		if (jsonReview.id != null) {
			updateReview()
				.then((review) => {
					console.log('review updated');
					console.log(review);
				});
		} else {
			addReview()
				.then((review) => {
					console.log('review inserted');
					console.log(review);
					reviews.push(review);
				});
		}
	};

	return (
		<>
			<div className={'row'}>Reviews</div>
			<div className={'table'}>
				{
					reviews.map((review) =>
						<div className={'row'} onClick={() => setJsonReview(review)}>
							<div className={'col width_40'}>{review.id}</div>
							<div className={'col width_200'}>{review.date}</div>
							<div className={'col width_40'}>{review.rating}</div>
							<div className={'col width_200'}>{review.detail}</div>
						</div>
					)
				}
			</div>
			<div>
				<span>New review</span><br/>
				<textarea rows={12}
				          cols={200}
				          value={JSON.stringify(jsonReview, null, 4)}
				          onChange={e => setJsonReview(e.target.value as unknown as Review)}
				/><br/>
				<button onClick={saveReview}>Update films</button>
			</div>
		</>
	);
};