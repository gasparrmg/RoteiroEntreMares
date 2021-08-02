package com.lasige.roteiroentremares.ui.dashboard.adapters.artefactos;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.android.roteiroentremares.R;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.lasige.roteiroentremares.data.model.Artefacto;
import com.lasige.roteiroentremares.data.model.ArtefactoTurma;
import com.lasige.roteiroentremares.util.TimeUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ArtefactoTurmaAdapter extends ListAdapter<ArtefactoTurma, RecyclerView.ViewHolder> {

    private Context context;

    private OnItemClickListener listener;
    private OnItemLongClickListener listenerLong;

    public ArtefactoTurmaAdapter(Context context) {
        super(DIFF_CALLBACK);

        this.context = context;
    }

    private static final DiffUtil.ItemCallback<ArtefactoTurma> DIFF_CALLBACK = new DiffUtil.ItemCallback<ArtefactoTurma>() {
        @Override
        public boolean areItemsTheSame(@NonNull ArtefactoTurma oldItem, @NonNull ArtefactoTurma newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ArtefactoTurma oldItem, @NonNull ArtefactoTurma newItem) {
            if (oldItem != null || oldItem.getDescription() != null) {
                return oldItem.getTitle().equals(newItem.getTitle()) &&
                        oldItem.getContent().equals(newItem.getContent()) &&
                        oldItem.getType() == newItem.getType() &&
                        oldItem.getDescription().equals(newItem.getDescription()) &&
                        oldItem.getReceivedAt().equals(newItem.getReceivedAt()) &&
                        oldItem.getLatitude().equals(newItem.getLatitude()) &&
                        oldItem.getLongitude().equals(newItem.getLongitude()) &&
                        oldItem.getCodigoTurma().equals(newItem.getCodigoTurma());
            }

            return false;
        }
    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                View imageItemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.artefacto_turma_image_item, parent, false);
                return new ViewHolderImage(imageItemView);
            case 2:
                View audioItemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.artefacto_turma_audio_item, parent, false);
                return new ViewHolderAudio(audioItemView);
            case 3:
                View videoItemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.artefacto_turma_video_item, parent, false);
                return new ViewHolderVideo(videoItemView);
            default:
                View textItemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.artefacto_turma_text_item, parent, false);
                return new ViewHolderText(textItemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Where we get the data to the Views
        ArtefactoTurma currentArtefacto = getItem(position);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        switch (holder.getItemViewType()) {
            case 1:
                // Image Artefact
                ViewHolderImage viewHolderImage = (ViewHolderImage) holder;
                viewHolderImage.textViewTitle.setText(currentArtefacto.getTitle());
                viewHolderImage.textViewDescription.setText(currentArtefacto.getDescription());

                viewHolderImage.textViewDate.setText(sdf.format(currentArtefacto.getReceivedAt()));
                viewHolderImage.textViewAutor.setText(currentArtefacto.getAutor());

                File imageFile = new File(currentArtefacto.getContent());

                if (imageFile.exists()) {
                    //viewHolderImage.imageViewPhoto.setImageURI(Uri.fromFile(new File(currentArtefacto.getContent())));

                    Glide.with(context)
                            .load(new File(currentArtefacto.getContent()))
                            .placeholder(android.R.drawable.ic_media_play)
                            .into(viewHolderImage.imageViewPhoto);
                } else {
                    Log.e("ARTEFACTO_ADAPTER", "Não foi possível encontrar a imagem.");
                }
                break;
            case 2:
                // Audio Artefact
                ViewHolderAudio viewHolderAudio = (ViewHolderAudio) holder;
                viewHolderAudio.textViewTitle.setText(currentArtefacto.getTitle());
                viewHolderAudio.textViewDescription.setText(currentArtefacto.getDescription());

                viewHolderAudio.textViewDate.setText(sdf.format(currentArtefacto.getReceivedAt()));
                viewHolderAudio.textViewAutor.setText(currentArtefacto.getAutor());

                viewHolderAudio.currentAudioPath = currentArtefacto.getContent();
                break;
            case 3:
                // Video Artefact
                ViewHolderVideo viewHolderVideo = (ViewHolderVideo) holder;
                viewHolderVideo.textViewTitle.setText(currentArtefacto.getTitle());
                viewHolderVideo.textViewDescription.setText(currentArtefacto.getDescription());

                viewHolderVideo.textViewDate.setText(sdf.format(currentArtefacto.getReceivedAt()));
                viewHolderVideo.textViewAutor.setText(currentArtefacto.getAutor());

                File videoFile = new File(currentArtefacto.getContent());

                if (videoFile.exists()) {
                    //viewHolderImage.imageViewPhoto.setImageURI(Uri.fromFile(new File(currentArtefacto.getContent())));

                    Glide.with(context)
                            .load(videoFile)
                            .placeholder(android.R.drawable.ic_media_play)
                            .into(viewHolderVideo.imageViewThumbnail);
                } else {
                    Log.e("ARTEFACTO_ADAPTER", "Não foi possível encontrar o vídeo.");
                }
                break;
            default:
                // Text Artefact
                ViewHolderText viewHolderTextAlt = (ViewHolderText) holder;
                viewHolderTextAlt.textViewTitle.setText(currentArtefacto.getTitle());
                viewHolderTextAlt.textViewContent.setText(currentArtefacto.getContent());
                viewHolderTextAlt.textViewDate.setText(sdf.format(currentArtefacto.getReceivedAt()));
                viewHolderTextAlt.textViewAutor.setText(currentArtefacto.getAutor());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }

    public ArtefactoTurma getArtefactoAt(int position) {
        return getItem(position);
    }

    private class ViewHolderText extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewContent;
        private TextView textViewDate;
        private TextView textViewAutor;
        private MaterialCardView cardView;


        public ViewHolderText(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            textViewContent = itemView.findViewById(R.id.textView_content);
            textViewDate = itemView.findViewById(R.id.textView_date);
            textViewAutor = itemView.findViewById(R.id.textView_autor);
            cardView = itemView.findViewById(R.id.cardview_artefacto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listenerLong.onLongItemClick(getItem(position), cardView);
                    }
                    return true;
                }
            });
        }
    }

    private class ViewHolderImage extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDate;
        private TextView textViewAutor;
        private ImageView imageViewPhoto;
        private MaterialCardView cardView;


        public ViewHolderImage(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            textViewDescription = itemView.findViewById(R.id.textView_description);
            textViewDate = itemView.findViewById(R.id.textView_date);
            textViewAutor = itemView.findViewById(R.id.textView_autor);
            imageViewPhoto = itemView.findViewById(R.id.imageview_picture);
            cardView = itemView.findViewById(R.id.cardview_artefacto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listenerLong.onLongItemClick(getItem(position), cardView);
                    }
                    return true;
                }
            });
        }
    }

    private class ViewHolderAudio extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDate;
        private TextView textViewAutor;
        private MaterialCardView cardView;
        private SeekBar seekBarAudio;
        private TextView textViewAudioDuration;
        private ImageButton imageButtonPlay;
        private ImageButton imageButtonPause;

        private MediaPlayer mediaPlayer;
        private Handler handlerSeekBar;
        private Runnable updateSeekBar;

        private String currentAudioPath;
        private boolean isPlayingAudio = false;

        public ViewHolderAudio(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            textViewDescription = itemView.findViewById(R.id.textView_description);
            textViewDate = itemView.findViewById(R.id.textView_date);
            textViewAutor = itemView.findViewById(R.id.textView_autor);
            cardView = itemView.findViewById(R.id.cardview_artefacto);
            imageButtonPlay = itemView.findViewById(R.id.imagebutton_play_audio);
            imageButtonPause = itemView.findViewById(R.id.imagebutton_pause_audio);
            seekBarAudio = itemView.findViewById(R.id.seekbar_audio);
            textViewAudioDuration = itemView.findViewById(R.id.textView_audio_duration);

            imageButtonPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Play clicked", Toast.LENGTH_SHORT).show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listenerLong.onLongItemClick(getItem(position), cardView);
                    }
                    return true;
                }
            });

            imageButtonPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playAudio();
                }
            });

            imageButtonPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pauseAudio();
                }
            });

            seekBarAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    if (mediaPlayer != null) {
                        pauseAudio();
                    }
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    int progress = seekBar.getProgress();

                    if (mediaPlayer != null) {
                        mediaPlayer.seekTo(progress);
                        resumeAudio();
                    }
                }
            });
        }

        private void playAudio() {
            imageButtonPause.setVisibility(View.VISIBLE);
            imageButtonPlay.setVisibility(View.GONE);

            if (!isPlayingAudio) {
                isPlayingAudio = true;

                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(currentAudioPath);
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    seekBarAudio.setMax(mediaPlayer.getDuration());
                    textViewAudioDuration.setText(TimeUtils.getTimeString(mediaPlayer.getDuration()));
                    textViewAudioDuration.setVisibility(View.VISIBLE);

                    handlerSeekBar = new Handler();
                    updateRunnable();
                    handlerSeekBar.postDelayed(updateSeekBar, 0);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        isPlayingAudio = false;

                        seekBarAudio.setProgress(0);

                        handlerSeekBar.removeCallbacks(updateSeekBar);

                        imageButtonPause.setVisibility(View.GONE);
                        imageButtonPlay.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                resumeAudio();
            }
        }

        private void resumeAudio() {
            imageButtonPause.setVisibility(View.VISIBLE);
            imageButtonPlay.setVisibility(View.GONE);

            mediaPlayer.start();

            updateRunnable();
            handlerSeekBar.postDelayed(updateSeekBar,0);
        }

        private void pauseAudio() {
            imageButtonPause.setVisibility(View.GONE);
            imageButtonPlay.setVisibility(View.VISIBLE);

            mediaPlayer.pause();

            handlerSeekBar.removeCallbacks(updateSeekBar);
        }

        private void updateRunnable() {
            updateSeekBar = new Runnable() {
                @Override
                public void run() {
                    seekBarAudio.setProgress(mediaPlayer.getCurrentPosition());
                    handlerSeekBar.postDelayed(this, 500);
                }
            };
        }
    }

    private class ViewHolderVideo extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDate;
        private TextView textViewAutor;
        private ImageView imageViewThumbnail;
        private MaterialCardView cardView;



        public ViewHolderVideo(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            textViewDescription = itemView.findViewById(R.id.textView_description);
            textViewDate = itemView.findViewById(R.id.textView_date);
            textViewAutor = itemView.findViewById(R.id.textView_autor);
            imageViewThumbnail = itemView.findViewById(R.id.imageview_thumbnail);
            cardView = itemView.findViewById(R.id.cardview_artefacto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listenerLong.onLongItemClick(getItem(position), cardView);
                    }
                    return true;
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ArtefactoTurma artefactoTurma);
    }

    public interface OnItemLongClickListener {
        void onLongItemClick(ArtefactoTurma artefactoTurma, MaterialCardView cardView);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.listenerLong = listener;
    }
}
